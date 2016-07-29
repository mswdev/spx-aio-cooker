package scripts.SPXAIOCooker;

import com.allatori.annotations.DoNotRename;
import org.tribot.api.General;
import org.tribot.api.Timing;
import scripts.SPXAIOCooker.data.Vars;
import scripts.SPXAIOCooker.data.enums.Location;
import scripts.TribotAPI.Client;
import scripts.TribotAPI.SendReportData;
import scripts.TribotAPI.gui.AbstractGUIController;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import scripts.TribotAPI.util.Utility;

public class GUIController extends AbstractGUIController {

    @FXML
    @DoNotRename
    private ResourceBundle resources;

    @FXML
    @DoNotRename
    private URL location;

    @FXML
    @DoNotRename
    private Button start;

    @FXML
    @DoNotRename
    private TextField food_names;

    @FXML
    @DoNotRename
    private ComboBox<Location> cooking_location;

    @FXML
    @DoNotRename
    private CheckBox make_wines;

    @FXML
    @DoNotRename
    private Spinner<Integer> level_to_stop;

    @FXML
    @DoNotRename
    private Spinner<Integer> amount_to_cook;

    @FXML
    @DoNotRename
    private TextArea bug_clientdebug;

    @FXML
    @DoNotRename
    private TextArea bug_stacktrace;

    @FXML
    @DoNotRename
    private TextArea bug_description;

    @FXML
    @DoNotRename
    private TextArea bug_botdebug;

    @FXML
    @DoNotRename
    private Button send_report;

    @FXML
    @DoNotRename
    private Label report_sent;

    @FXML
    @DoNotRename
    private Label report_spam;

    @FXML
    @DoNotRename
    private Hyperlink join_discord;

    @FXML
    @DoNotRename
    private Hyperlink add_skype;

    @FXML
    @DoNotRename
    private Hyperlink private_message;

    @FXML
    @DoNotRename
    private Hyperlink website_link;

    @FXML
    @DoNotRename
    void initialize() {
        cooking_location.getItems().setAll(Location.values());

        cooking_location.getSelectionModel().select(0);

        join_discord.setOnAction((event) -> Utility.openURL("https://discordapp.com/invite/0yCbdv5qTOWmxUD5"));

        add_skype.setOnAction((event -> Utility.openURL("http://hatscripts.com/addskype/?sphiin.x")));

        private_message.setOnAction((event -> Utility.openURL("https://tribot.org/forums/profile/176138-sphiinx/")));

        website_link.setOnAction((event -> Utility.openURL("http://spxscripts.com/")));

        send_report.setOnAction((event) -> {
            if (SendReportData.LAST_SENT_TIME <= 0 || Timing.timeFromMark(SendReportData.LAST_SENT_TIME) > 60000) {
                if (!SendReportData.sendReportData(Client.getManifest(Main.class).name(), Client.getManifest(Main.class).version(), bug_description.getText(), bug_stacktrace.getText(), bug_clientdebug.getText(), bug_botdebug.getText()))
                    report_sent.setText("UH OH! There seems to have been an error with your report!");

                report_sent.setOpacity(1);
                report_spam.setOpacity(0);
                SendReportData.LAST_SENT_TIME = Timing.currentTimeMillis();
            } else {
                report_sent.setOpacity(0);
                report_spam.setOpacity(1);
            }
        });

        start.setOnAction((event) -> {
            Vars.get().food_names = food_names.getText().split(",");
            Vars.get().location = cooking_location.getSelectionModel().getSelectedItem();
            Vars.get().level_to_stop = Integer.parseInt(level_to_stop.getValue().toString());
            Vars.get().amount_to_cook = Integer.parseInt(amount_to_cook.getValue().toString());
            Vars.get().is_making_wine = make_wines.isSelected();
            getGUI().close();
        });
    }

}
