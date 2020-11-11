package tessterton;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

public class JaxbStartup {
    public static void main(String[] args) {
        TestBean tb = new TestBean();
        try {
            JAXBContext context = JAXBContext.newInstance(TestBean.class);
            context.createMarshaller().marshal(tb,System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
