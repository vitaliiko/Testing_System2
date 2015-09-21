import supporting.IOFileHandling;
import userGI.ShowTaskGI;

public class Test {

    public static void main(String[] args) {
//        new ShowTaskGI();

        new ShowTaskGI(IOFileHandling.loadTestTask("Модуль_1"));
    }
}
