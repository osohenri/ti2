import com.microsoft.cognitiveservices.speech.*;

public class SpeechToText {

    public static void main(String[] args) {
        // Substitua pelos dados do seu recurso no Azure
        String subscriptionKey = System.getenv("AZURE_SPEECH_KEY"); // Obtém a chave da variável de ambiente AZURE_SPEECH_KEY
        String serviceRegion = "westus3"; // Região do serviço

        try {
            // Configuração do serviço
            SpeechConfig config = SpeechConfig.fromSubscription(
                subscriptionKey,
                serviceRegion
            );

            // Carregar o arquivo de áudio .wav
            AudioConfig audioInput = AudioConfig.fromWavFileInput("audio.wav"); // arquivo na mesma pasta
            SpeechRecognizer recognizer = new SpeechRecognizer(
                config,
                audioInput
            );

            // Realizar reconhecimento
            System.out.println("Reconhecendo fala do arquivo...");
            SpeechRecognitionResult result = recognizer
                .recognizeOnceAsync()
                .get();

            // Verificar resultado
            if (result.getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("Texto reconhecido: " + result.getText());
            } else {
                System.out.println(
                    "Falha no reconhecimento. Motivo: " + result.getReason()
                );
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar o áudio:");
            e.printStackTrace();
        }
    }
}
