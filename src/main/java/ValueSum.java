import org.infai.seits.sepl.operators.Message;
import org.infai.seits.sepl.operators.OperatorInterface;

public class ValueSum implements OperatorInterface {

    private static Double currentValue = 0.0;

    @Override
    public void run(Message message) {
        currentValue += message.getInput("value").getValue();
        message.output("sum", (Math.round(currentValue * 1000.0) / 1000.0));
    }

    @Override
    public void config(Message message) {
        message.addInput("value");
    }
}
