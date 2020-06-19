package fx.charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
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
public class AudioBarChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Frequency Bands");
        NumberAxis yAxis = new NumberAxis(0, 50, 10);
        yAxis.setLabel("Magnitudes");
        yAxis.setTickLabelFormatter(new DefaultFormatter(yAxis, null, "dB"));
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        String audioBarChartCss = getClass().getResource("AudioBarChart.css").toExternalForm();
        barChart.getStylesheets().add(audioBarChartCss);
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);
        barChart.setBarGap(0);
        barChart.setCategoryGap(1);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setTitle("Live Audio Spectrum Data");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Data Series 1");
        XYChart.Data<String, Number>[] series1Data = new XYChart.Data[128];
        String[] categories = new String[128];
        for (int i = 0; i < series1Data.length; i++) {
            categories[i] = Integer.toString(i + 1);
            series1Data[i] = new XYChart.Data<String, Number>(categories[i], 50);
            series1.getData().add(series1Data[i]);
        }
        barChart.getData().add(series1);

        String AUDIO_URI = getClass().getResource("/fx/ensemble/shared-resources/oow2010-2.mp4").toExternalForm();
        Media audioMedia = new Media(AUDIO_URI);
        MediaPlayer audioMediaPlayer = new MediaPlayer(audioMedia);
        audioMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        AudioSpectrumListener audioSpectrumListener = (double timestamp, double duration,
                                                       float[] magnitudes, float[] phases) -> {
            for (int i = 0; i < series1Data.length; i++) {
                series1Data[i].setYValue(magnitudes[i] + 60);
            }
        };
        audioMediaPlayer.setAudioSpectrumListener(audioSpectrumListener);

        primaryStage.setScene(new Scene(barChart));
        primaryStage.show();
        audioMediaPlayer.play();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
