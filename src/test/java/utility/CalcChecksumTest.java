package utility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CalcChecksumTest {
    private File f;

    @Before
    public void before() throws URISyntaxException {
        f = new File(CalcChecksumTest.class.getResource("checksumFile").toURI());
    }

    @Test
    public void checksumStrByte() {
        final String data = "adflkaeflaefamadjfkaelfadflaekfkada";
        final long sum = 4208856556L;

        Assert.assertEquals(sum,CalcChecksum.checksum(data));
        Assert.assertEquals(sum,CalcChecksum.checksum(data.getBytes()));
    }

    @Test
    public void invalidFile() {
        Assert.assertEquals(1,CalcChecksum.checksum(new File("akdjfalkdjf")));
    }

    @Test
    public void sameChecksum() throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get(f.getPath()));
        final String str = new String(bytes, StandardCharsets.UTF_8);
        final long sum = 94318608;

        Assert.assertEquals(sum, CalcChecksum.checksum(f));
        Assert.assertEquals(sum, CalcChecksum.checksum(bytes));
        Assert.assertEquals(sum, CalcChecksum.checksum(str));
    }

    @Test
    public void argumentsNull() {
        Assert.assertEquals(1,CalcChecksum.checksum((String)null));
        Assert.assertEquals(1,CalcChecksum.checksum((byte[])null));
        Assert.assertEquals(1,CalcChecksum.checksum((File)null));
    }
}