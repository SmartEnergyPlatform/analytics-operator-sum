import com.jayway.jsonpath.JsonPath;
import org.infai.seits.sepl.operators.Builder;
import org.infai.seits.sepl.operators.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ValueSumTest {

    static ValueSum valueSum;
    protected JSONObject ob1 = new JSONObject();
    protected JSONObject ob2 = new JSONObject();

    public ValueSumTest (){
        ob1.put("device_id", "1").put("value", new JSONObject().put("reading", new JSONObject().put("value", new Double(5))));
        ob2.put("device_id", "1").put("value", new JSONObject().put("reading", new JSONObject().put("value", new Double(10))));
    }

    @Test
    public void testRun(){
        valueSum  = new ValueSum();
        Builder builder = new Builder("1", "1");
        JSONArray config = new JSONArray().put(new JSONObject().put("Name","test")
                .put("FilterType", "DeviceId")
                .put("FilterValue", "1")
                .put("Mappings", new JSONArray().put(
                        new JSONObject().put("Source", "value.reading.value").put("Dest","value"))
                ));
        Message message = new Message(builder.formatMessage(ob1.toString()));
        message.setConfig(config.toString());
        valueSum.config(message);
        valueSum.run(message);
        message.setMessage(builder.formatMessage(ob2.toString()));
        valueSum.run(message);
        Assert.assertEquals(new Double(15.0), JsonPath.parse(message.getMessageString()).read("$.analytics.sum"));
    }
}
