/*
 * Copyright (c) 2008, 2018, Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fx.ensemble.charts;


import javafx.application.Application;
import javafx.scene.Parent;
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
 * Bar chart that shows audio spectrum of a music file being played.
 */
public class AudioBarChartApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        audioSpectrumListener = (double timestamp, double duration,
                                 float[] magnitudes, float[] phases) -> {
            for (int i = 0; i < series1Data.length; i++) {
                series1Data[i].setYValue(magnitudes[i] + 60);
            }
        };

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, 50, 10);
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        final String audioBarChartCss = getClass().getResource("/fx/ensemble/shared-resources/AudioBarChart.css").toExternalForm();
        bc.getStylesheets().add(audioBarChartCss);
        bc.setLegendVisible(false);
        bc.setAnimated(false);
        bc.setBarGap(0);
        bc.setCategoryGap(1);
        bc.setVerticalGridLinesVisible(false);
        // setup chart
        bc.setTitle("Live Audio Spectrum Data");
        xAxis.setLabel("Frequency Bands");
        yAxis.setLabel("Magnitudes");
        yAxis.setTickLabelFormatter(new DefaultFormatter(yAxis, null, "dB"));
        // add starting data
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Data Series 1");
        //noinspection unchecked
        series1Data = new XYChart.Data[128];
        String[] categories = new String[128];
        for (int i = 0; i < series1Data.length; i++) {
            categories[i] = Integer.toString(i + 1);
            series1Data[i] = new XYChart.Data<String, Number>(categories[i], 50);
            series1.getData().add(series1Data[i]);
        }
        bc.getData().add(series1);

        primaryStage.setScene(new Scene(bc));
        primaryStage.show();
        this.startAudio();
    }


    @Override
    public void stop() {
        this.stopAudio();
    }

    private void startAudio() {
        if (PLAY_AUDIO) {
            getAudioMediaPlayer()
                    .setAudioSpectrumListener(audioSpectrumListener);
            getAudioMediaPlayer().play();
        }
    }

    private void stopAudio() {
        if (getAudioMediaPlayer().getAudioSpectrumListener() == audioSpectrumListener) {
            getAudioMediaPlayer().pause();
        }
    }

    private MediaPlayer getAudioMediaPlayer() {
        if (audioMediaPlayer == null) {
            final String AUDIO_URI = getClass().getResource("/fx/ensemble/shared-resources/oow2010-2.mp4").toExternalForm();
            Media audioMedia = new Media(AUDIO_URI);
            audioMediaPlayer = new MediaPlayer(audioMedia);
            audioMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        return audioMediaPlayer;
    }


    public static void main(String[] args) {
        launch(args);
    }

    private XYChart.Data<String, Number>[] series1Data;
    private AudioSpectrumListener audioSpectrumListener;
    private MediaPlayer audioMediaPlayer;
    private static final boolean PLAY_AUDIO = Boolean.parseBoolean(System.getProperty("demo.play.audio", "true"));
}
