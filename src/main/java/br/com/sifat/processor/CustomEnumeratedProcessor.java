package br.com.sifat.processor;

import org.apache.log4j.Logger;
import org.hibernate.AnnotationException;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
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

@SupportedAnnotationTypes("javax.annotation.Enumerated")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CustomEnumeratedProcessor extends AbstractProcessor {
    private static final Logger log = Logger.getLogger(CustomEnumeratedProcessor.class);

    public static void process() {

        // configura reflection scanner
        Reflections reflections =
                new Reflections(
                        new ConfigurationBuilder()
                                .setUrls(ClasspathHelper.forPackage("br.com.sifat.model"))
                                .setScanners(new FieldAnnotationsScanner(), new MethodAnnotationsScanner()));

        // procura anotações nos metodos e campos
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Enumerated.class);
        Set<Method> methods = reflections.getMethodsAnnotatedWith(Enumerated.class);

        // junta os metodos e os campos em uma unica lista para facilitar a iteracao
        ArrayList<AccessibleObject> members = new ArrayList<AccessibleObject>();
        members.addAll(fields);
        members.addAll(methods);

        // itera sobre a lista
        for (AccessibleObject member : members) {

            // altrera o value de Enmerated para Enumtype.String programaticamente
            Enumerated enumerated = member.getAnnotation(Enumerated.class);
            changeAnnotationValue(enumerated, "value", EnumType.STRING);

            // necessario anotacao column estar presente no campo ou metodo
            if (!member.isAnnotationPresent(Column.class)) {
                throw new AnnotationException("Necessário anotação @Column e @Enumerated no campo.");
            }

            // descrobindo o tipo da enum
            Class<Enum> type = resolveType(member);

            try {

                System.out.println(type);
                // invoca o metodo .values() e obtém um array com os valoes da enum
                Enum<?>[] values = (Enum<?>[]) type.getDeclaredMethod("values").invoke(null);

                // constroi sql para column definition
                // primeiro valor da Enum sera considerado como default
                String columnDefinition = createColumnDefinitionStamment(values);

                // altera o valor de columnDefinition programaticamente
                Column column = member.getAnnotation(Column.class);
                changeAnnotationValue(column, "columnDefinition", columnDefinition);

                String campo = ((Member) member).getDeclaringClass().getName() + "." + ((Member) member).getName();

                log.info("Campo: " + campo + ", Enum: " + type.getName() + ", columnDefinition: " + column.columnDefinition());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método utilitario para alterar um campo dentro de uma anotacao em tempo de execucao
     *
     * <p>Changes the annotation value for the given key of the given annotation to newValue
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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        process();
        return true;
    }

    @SuppressWarnings("unchecked")
    private static Class<Enum> resolveType(AccessibleObject member) {
        if (member instanceof Field) { // item anotado é um campo ?
            Field f = (Field) member;
            try {
                return (Class<Enum>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
            } catch (Exception e) {
                return (Class<Enum>) f.getType(); // considera o tipo do campo
            }
        } else { // é um metodo ?
            Method m = (Method) member;
            try {
                return (Class<Enum>) ((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments()[0];
            } catch (Exception e) {
                return (Class<Enum>) m.getReturnType(); // considera o tipo deretorno do metodo
            }
        }
    }

    public static final String createColumnDefinitionStamment(Enum[] values) {
        return Stream.of(values)
                .map(Enum::name)
                .collect(Collectors.joining(
                        "', '", "enum('", "') NOT NULL DEFAULT '" + values[0] + "'"));
    }
}
