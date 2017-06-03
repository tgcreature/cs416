import java.util.*;

public class BatchTest {
    public static void main(String[] args) {
        PostfixEval evaluator = new PostfixEval("Title would go here");
        Reporter.interactive = false;

        Scanner batchIn = new Scanner( System.in );
        while ( batchIn.hasNextLine() ) {
            String input = batchIn.nextLine();
            Number value = evaluator.evaluateExpression( input );
            if ( value != null )
                Reporter.reportEval( input, value );
            else
                Reporter.reportFail( input, "Evaluation failed" );
        }
    }
}



