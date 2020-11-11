package tessterton;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestBean {

    @XmlElement
    private String field = "TestField";
}
