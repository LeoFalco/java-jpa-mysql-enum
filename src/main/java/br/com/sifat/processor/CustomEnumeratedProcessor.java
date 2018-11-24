package br.com.sifat.processor;

import lombok.extern.log4j.Log4j;
import org.hibernate.AnnotationException;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j
public class CustomEnumeratedProcessor {

    public static void process() {

        // configura reflection scanner
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("br.com.sifat.model"))
                .setScanners(new FieldAnnotationsScanner(), new MethodAnnotationsScanner())
        );

        // procura anotações nos metodos e campos
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Enumerated.class);
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Enumerated.class);


        // junta os metodos e os campos em uma unica lista para facilitar a iteracao
        var members = new ArrayList<AccessibleObject>();
        members.addAll(fields);
        members.addAll(methods);

        // itera sobre a lista
        for (var member : members) {

            // altrera o value de Enmerated para Enumtype.String programaticamente
            Enumerated enumerated = member.getAnnotation(Enumerated.class);
            changeAnnotationValue(enumerated, "value", EnumType.STRING);

            // necessario anotacao column estar presente no campo ou metodo
            if (!member.isAnnotationPresent(Column.class)) {
                throw new AnnotationException("Necessário anotação @Column e @Enumerated no campo.");
            }

            // descrobindo o tipo da enum
            Class<Enum> type;
            if (member instanceof Field) { // item anotado é um campo ?
                var f = (Field) member;
                //noinspection unchecked
                type = (Class<Enum>) f.getType(); // considera o tipo do campo
            } else { // é um metodo ?
                var m = (Method) member;
                //noinspection unchecked
                type = (Class<Enum>) m.getReturnType(); // considera o retorno do método
            }

            try {
                // invoca o metodo .values() e obtém um array com os valoes da enum
                var values = (Enum[]) type.getDeclaredMethod("values").invoke(null);

                // constroi sql para column definition
                // primeiro valor da Enum sera considerado como default
                String columnDefinition = Stream.of(values)
                        .map(Enum::name)
                        .collect(Collectors.joining("', '",
                                "enum('",
                                "') NOT NULL DEFAULT '" + values[0] + "'"
                        ));

                // altera o valor de columnDefinition programaticamente
                Column column = member.getAnnotation(Column.class);
                changeAnnotationValue(column, "columnDefinition", columnDefinition);

                String campo = ((Member) member).getDeclaringClass().getName() + "." + ((Member) member).getName();

                log.info("campo: " + campo + ", columnDefinition: " + column.columnDefinition());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * Método utilitario para alterar um campo dentro de uma anotacao em tempo de execucao
     * <p>
     * Changes the annotation value for the given key of the given annotation to newValue
     */
    public static void changeAnnotationValue(Annotation annotation, String key, Object newValue) {
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key, newValue);
    }
}