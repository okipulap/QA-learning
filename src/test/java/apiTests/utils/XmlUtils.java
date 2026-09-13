package apiTests.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;

public class XmlUtils {

    public static <T> T fromXml(String xml, Class<T> tClass) {
        try {
            JAXBContext context = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return  tClass.cast(
                    unmarshaller.unmarshal(new StringReader(xml))
            );
        } catch(JAXBException e) {
            throw new RuntimeException("Ошибка десериализации XML", e);
        }
    }
}
