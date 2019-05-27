/*
 * Copyright (c) 2008, 2016, Oracle and/or its affiliates.
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
package fx.ensemble.controls;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Demonstrates some simple uses of rich text and TextFlow.
 */
public class TextFlowApp extends Application {

    private TextFlow textFlow;
    private Text textHello;
    private Text textBold;
    private Text textWorld;

    @Override
    public void start(Stage primaryStage) throws Exception {
        String family = "Helvetica";
        double size = 20;

        // Simple example
        textFlow = new TextFlow();
        textHello = new Text("Hello ");
        textHello.setFont(Font.font(family, size));
        textBold = new Text("Bold");
        textBold.setFont(Font.font(family, FontWeight.BOLD, size));
        textWorld = new Text(" World");
        textWorld.setFont(Font.font(family, FontPosture.ITALIC, size));
        textFlow.getChildren().addAll(textHello, textBold, textWorld);

        // Example with embedded objects
        TextFlow embeddedFlow = new TextFlow();
        Text textEO1 = new Text("Lets have ");
        textEO1.setFont(Font.font(family, size));
        Text textEO2 = new Text("embedded objects: a Rectangle ");
        textEO2.setFont(Font.font(family, FontWeight.BOLD, size));
        Rectangle rect = new Rectangle(80, 60);
        rect.setFill(null);
        rect.setStroke(Color.GREEN);
        rect.setStrokeWidth(5);
        Text textEO3 = new Text(", then a button ");
        Button button = new Button("click me");
        Text textEO4 = new Text(", and finally an image ");
        String URL = "/fx/ensemble/shared-resources/oracle.png";
        Image image = new Image(getClass().getResourceAsStream(URL));
        ImageView imageView = new ImageView(image);
        Text textEO5 = new Text(".");
        textEO5.setFont(Font.font(family, size));

        embeddedFlow.getChildren().addAll(textEO1, textEO2, rect, textEO3,
                button, textEO4, imageView, textEO5);

        final VBox vbox = new VBox(18);
        VBox.setMargin(textFlow, new Insets(5, 5, 0, 5));
        VBox.setMargin(embeddedFlow, new Insets(0, 5, 5, 5));
        vbox.getChildren().addAll(textFlow, embeddedFlow);

        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
