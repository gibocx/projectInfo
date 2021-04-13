package insert;

import db.RunQuery;
import download.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class InsertCSVTest {
    RunQuery run;
    final Map<String, String> data = new HashMap<String, String>();

    @Before
    public void before() {
        run = Mockito.mock(RunQuery.class);
        Mockito.when(run.add(any(String[].class))).thenReturn(true);

        data.put("query","insert ?;");
        data.put("dataType","CSV");
        data.put("schema","schema");
    }

    @Test
    public void action() {
        for(int i = 1; i <= 8;i++) {
            data.put("argument"+i,"row"+(9-i));
        }

        ArgumentCaptor<String[]> strs = ArgumentCaptor.forClass(String[].class);

        InsertInfo in = new InsertInfo(data);
        InsertCSV csv = new InsertCSV(in,run);
        csv.insert("1,2,3,4,5,6,7,8,9,10",new Category("test"));


        data.put("query","insert ?;");
        data.put("dataType","CSV");
        data.put("schema","schema");
        data.put("delimiter",";");
        in = new InsertInfo(data);
        csv = new InsertCSV(in,run);
        csv.insert("1;2;3;4;5;6;7;8;9;10",new Category("test"));

        verify(run, times(2)).add(strs.capture());

        String[] values = new String[]{"9","8","7","6","5","4","3","2"};

        Assert.assertArrayEquals(values,strs.getAllValues().get(0));
        Assert.assertArrayEquals(values,strs.getAllValues().get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidRunQuery() {
        new InsertCSV(new InsertInfo(data),null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInsertInfo() {
        new InsertCSV(null, run);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidRow() {
        data.put("argument1","rowA");

        new InsertCSV(new InsertInfo(data),run);
    }
}