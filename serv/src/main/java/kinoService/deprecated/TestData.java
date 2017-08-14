package kinoService.deprecated;

import java.util.Date;

/**
 * Created by Nik on 18.06.2017.
 */
@Deprecated
public class TestData {
    public Date timeLong;
    public String title;

    @Override
    public String toString() {
        return "TestData{" +
                "timeLong=" + timeLong +
                ", title='" + title + '\'' +
                '}';
    }
}
