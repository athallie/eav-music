package com.eavmusic.eavmusic.Controller;

import com.eavmusic.eavmusic.Helper.FileUtil;
import com.eavmusic.eavmusic.Model.Music;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Music> tableView;
    private TableView.TableViewSelectionModel<Music> selectionModel;
    private final ObservableList<Music> allMusic = FXCollections.observableArrayList();
    private Stage primaryStage;

    /*
    * Inisialisasi
    * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.selectionModel = tableView.getSelectionModel();
        searchMusic();
        try {
            setButtonIcons();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        setColumns();
        setSelectionModel();
        setColumnEqualWidth();
        setTablePlaceholder();
        tableView.setItems(allMusic);
    }

    /*
    * Pencarian File Musik.
    * Pengguna memilih satu folder dalam pop window directory chooser.
    * Algoritma pencarian berada di class FileUtil.
    * Semua file musik dalam folder dan subfolder disimpan dalam LinkedList.
    * LinkedList diberikan ke method handleAddItem.
    * */
    public void scanDirectoriesForMusic(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(this.primaryStage);

        LinkedList<File> musicFiles = new LinkedList<>();
        FileUtil.searchMp3Files(selectedDirectory.listFiles(), musicFiles);

        handleAddItem(musicFiles);
    }

    /*
    * Method guna mendapatkan stage aplikasi untuk
    * argumen DirectoryChooser.showDialog().
    * */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /*
    * Method untuk mendapatkan Item aktif dalam tabel.
    * */
    private Music getSelectedItem() {
        return selectionModel.getSelectedItem();
    }

    @FXML
    private void handleAddItem(LinkedList<File> musicFiles) {
        for (File musicFile : musicFiles) {
            Music music = new Music(musicFile.toPath());
            allMusic.add(music);
        }
        Platform.runLater(() -> tableView.refresh());
    }

    /*
    * Kontrol Musik
    * Musik akan di play/pause/stop
    * setelah button yang relevan diklik.
    * Implementasi play/pause/stop ada di class MediaPlayerManager.
    * */

    @FXML
    private void pause() {
        pauseButton.setOnAction(actionEvent -> MediaPlayerManager.pause());
    }

    @FXML
    private void play() {
        playButton.setOnAction(
                actionEvent ->
                {
                    if (MediaPlayerManager.lastMediaPlayer == null) {
                        MediaPlayerManager.play(getSelectedItem());
                    } else {
                        MediaPlayerManager.play();
                    }
                }
        );
    }

    @FXML
    private void stop() {
        stopButton.setOnAction(actionEvent -> MediaPlayerManager.stop());
    }

    /*
    * Method untuk memperbarui daftar musik dalam tabel.
    * */
    @FXML
    private void refresh() {
        refreshButton.setOnAction(actionEvent -> tableView.refresh());
    }


    /*
    * Pencarian Musik dalam Tabel
    * Method ini mencari musik seiring dengan
    * penambahan karakter di TextField.
    * FilteredList digunakan untuk menyimpan hasil pencarian.
    * SortedList digunakan untuk menyortirnya berdasarkan comparator TableView.
    * */
    private void searchMusic() {
        searchBar.textProperty().addListener(
                ((observableValue, oldValue, newValue) -> {
                    FilteredList<Music> filteredList = new FilteredList<>(allMusic, music -> {
                        return music.getTitle().toLowerCase().contains(newValue.toLowerCase())
                                || music.getArtist().toLowerCase().contains(newValue.toLowerCase())
                                || music.getAlbum().toLowerCase().contains(newValue.toLowerCase());
                    });
                    SortedList<Music> sortedList = new SortedList<>(filteredList);
                    sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                    tableView.setItems(sortedList);
                })
        );
    }

    /*
    * Method untuk konfigurasi gambar di button kontrol musik.
    * */
    private void setButtonIcons() throws FileNotFoundException {
        FileInputStream file = new FileInputStream(getClass().getResource("/images/play.png").getPath());
        Image playIcon = new Image(file);
        ImageView imv1 = new ImageView(playIcon);
        playButton.setGraphic(imv1);

        file = new FileInputStream(getClass().getResource("/images/pause.png").getPath());
        Image pauseIcon = new Image(file);
        ImageView imv2 = new ImageView(pauseIcon);
        pauseButton.setGraphic(imv2);

        file = new FileInputStream(getClass().getResource("/images/stop.png").getPath());
        Image stopIcon = new Image(file);
        ImageView imv3 = new ImageView(stopIcon);
        stopButton.setGraphic(imv3);
    }

    /*
    * Method untuk menyamaratakan panjang setiap kolom.
    * */
    private void setColumnEqualWidth() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    }

    /*
    * Method untuk membuat kolom,
    * mengkonfigurasinya, dan
    * menambahkannya ke TableView.
    * */
    private void setColumns() {
        TableColumn<Music, String> titleColumn = new TableColumn<>("Title");
        TableColumn<Music, String> artistColumn = new TableColumn<>("Artist");
        TableColumn<Music, String> albumColumn = new TableColumn<>("Album");
        TableColumn<Music, String> lengthColumn = new TableColumn<>("Length");

        titleColumn.setCellValueFactory(allMusic -> new SimpleStringProperty(allMusic.getValue().getTitle()));
        artistColumn.setCellValueFactory(allMusic -> new SimpleStringProperty(allMusic.getValue().getArtist()));
        albumColumn.setCellValueFactory(allMusic -> new SimpleStringProperty(allMusic.getValue().getAlbum()));
        lengthColumn.setCellValueFactory(allMusic -> new SimpleStringProperty(String.valueOf(allMusic.getValue().getDuration())));

        tableView.getColumns().addAll(titleColumn, artistColumn, albumColumn, lengthColumn);

        titleColumn.setCellFactory(
                tc ->
                {
                    TableCell<Music, String> tableCell = new TableCell<>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            setText(empty ? null : item);
                        }
                    };
                    tableCell.setOnMouseClicked(
                            e ->
                            {
                                if (!tableCell.isEmpty() && e.getClickCount() == 2) {
                                    System.out.println(tableCell.getItem());
                                    Music music = tableCell.getTableRow().getItem();
                                    if (MediaPlayerManager.lastMediaPlayer == music.getMediaPlayer()) {
                                        if (!MediaPlayerManager.isNull() && MediaPlayerManager.isPlaying()) {
                                            MediaPlayerManager.replay();
                                        }
                                    } else {
                                        if ((!MediaPlayerManager.isNull() && (MediaPlayerManager.isPlaying() || MediaPlayerManager.isPaused()))) {
                                            MediaPlayerManager.stop();
                                        }
                                        MediaPlayerManager.play(music);
                                    }
                                }
                            }
                    );
                    return tableCell;
                }
        );
    }

    /*
    * Method untuk mengkonfigurasi mode
    * pemilihan baris dalam TableView
    * menjadi SINGLE (hanya boleh satu baris saja).
    * */
    private void setSelectionModel() {
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }

    /*
    * Method untuk mengubah teks yang muncul
    * ketika TableView kosong.
    * */
    private void setTablePlaceholder() {
        tableView.setPlaceholder(new Label("No Music Found"));
    }
}
