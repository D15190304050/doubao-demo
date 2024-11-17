package boot.local.examples.doubao;

public class SingleRoundChatDemo
{
    public static final String API_KEY = "Replace with your API key.";
    public static final String MODEL_KEY = "Replace with your model key.";

    public static void main(String[] args)
    {
        DoubaoLlmSession session = new DoubaoLlmSession(API_KEY, MODEL_KEY);
        String answer = session.runChat("如何做红烧排骨？");
        System.out.println(answer);
    }
}
