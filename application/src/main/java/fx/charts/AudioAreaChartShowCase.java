package fx.charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.NumberAxis.DefaultFormatter;
import javafx.scene.chart.XYChart;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class AudioAreaChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis(0, 128, 8);
        xAxis.setLabel("Frequency Bands");
        NumberAxis yAxis = new NumberAxis(0, 50, 10);
        yAxis.setLabel("Magnitudes");
        yAxis.setTickLabelFormatter(new DefaultFormatter(yAxis, null, "dB"));

        AreaChart<Number, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        String audioAreaChartCss = getClass().getResource("AudioAreaChart.css").toExternalForm();
        areaChart.getStylesheets().add(audioAreaChartCss);
        areaChart.setLegendVisible(false);
        areaChart.setTitle("Live Audio Spectrum Data");
        areaChart.setAnimated(false);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Audio Spectrum");
        XYChart.Data<Number, Number>[] series1Data = new XYChart.Data[(int) xAxis.getUpperBound()];
        for (int i = 0; i < series1Data.length; i++) {
            series1Data[i] = new XYChart.Data<Number, Number>(i, 50);
            series.getData().add(series1Data[i]);
        }
        areaChart.getData().add(series);

        String AUDIO_URI = getClass().getResource("JavaRap_Audio.mp4").toExternalForm();
        Media audioMedia = new Media(AUDIO_URI);
        MediaPlayer audioMediaPlayer = new MediaPlayer(audioMedia);
        audioMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioSpectrumListener audioSpectrumListener = (double timestamp, double duration, float[] magnitudes, float[] phases) -> {
            for (int i = 0; i < series1Data.length; i++) {
                series1Data[i].setYValue(magnitudes[i] + 60);
            }
        };
        audioMediaPlayer.setAudioSpectrumListener(audioSpectrumListener);

        primaryStage.setScene(new Scene(areaChart));
        primaryStage.show();
        audioMediaPlayer.play();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
