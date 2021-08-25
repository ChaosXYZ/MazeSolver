import maze.*;
import maze.Tile.Type;
import maze.routing.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.IOException;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import java.util.HashMap;
import java.util.Map;
public class MazeApplication extends Application {
	private GridPane mazedisplay = new GridPane();
	private Maze maze; 
	private int theme;
	Scene scene;
	Scene mazescene;
	private RouteFinder mazesolution;
    Image wallsrc = new Image("maze/visualisation/wall.png");
    Image pathsrc = new Image("maze/visualisation/path.png");
    Image correctsrc = new Image("maze/visualisation/start.png");
    Image exitsrc = new Image("maze/visualisation/red.png");
	public static void main(String args[]) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("u07236av Maze Solver");
		VBox container = new VBox();
		mazescene = new Scene(container, 500, 500);
		Button loadmapbtn = new Button("Load Map");
        Button saveroutebtn = new Button("Save Route");
        Button loadroutebtn = new Button("Load Route");
        Button stepbtn = new Button("Step Forward");
        Button backbtn = new Button("Step Backward");
        Button themebtn = new Button("Desert Sand");
		HBox buttoncontainer = new HBox();
		buttoncontainer.getChildren().add(loadmapbtn);
		buttoncontainer.getChildren().add(saveroutebtn);
		buttoncontainer.getChildren().add(loadroutebtn);
		buttoncontainer.getChildren().add(stepbtn);
		buttoncontainer.getChildren().add(backbtn);
		buttoncontainer.getChildren().add(themebtn);
		
		loadmapbtn.setOnAction(e -> {
			stage.setScene(scene);
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Maze File");
			File filetoopen = fileChooser.showOpenDialog(stage);
			try {
				maze = Maze.fromTxt(filetoopen.getAbsolutePath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				maze=null;
			}
			
			mazesolution = new RouteFinder(maze);
			draw();
			
			
			container.getChildren().clear();
			container.getChildren().addAll(buttoncontainer, mazedisplay);
			stage.setScene(mazescene);
		
		
		});
		
		stepbtn.setOnAction(e -> {
			mazesolution.step();
			try {
				mazesolution.saver("tempdata");
				HashMap<Maze, RouteFinder> maptmp = mazesolution.loaded("tempdata");
		        maze = (Maze) maptmp.keySet().toArray()[0];
				mazesolution = maptmp.get(maze);
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			draw();
			
		});
		
		backbtn.setOnAction(e -> {
			mazesolution.back();
			try {
				mazesolution.saver("tempdata");
				HashMap<Maze, RouteFinder> maptmp = mazesolution.loaded("tempdata");
		        maze = (Maze) maptmp.keySet().toArray()[0];
				mazesolution = maptmp.get(maze);
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			draw();
			
		});
		
		themebtn.setOnAction(e -> {
			Random r = new Random();
			theme = r.nextInt(5);
			if (theme == 0) {
				themebtn.setText("Desert Sand");
				pathsrc = new Image("maze/visualisation/path.png");
				wallsrc = new Image("maze/visualisation/wall.png");
				draw();
			} else if (theme == 1) {
				themebtn.setText("Fiery Volcano");
				pathsrc = new Image("maze/visualisation/lavapath.png");
				wallsrc = new Image("maze/visualisation/lava.png");
				draw();
			} else if (theme == 2) {
				themebtn.setText("Beach Fun");
				pathsrc = new Image("maze/visualisation/sand.png");
				wallsrc = new Image("maze/visualisation/ocean.png");
				draw();
			} else if (theme == 3) {
				themebtn.setText("Forest Trail");
				pathsrc = new Image("maze/visualisation/leafpath.png");
				wallsrc = new Image("maze/visualisation/forest.png");
				draw();
			} else if (theme == 4) {
				themebtn.setText("Sky Palace");
				pathsrc = new Image("maze/visualisation/air.png");
				wallsrc = new Image("maze/visualisation/airbrick.png");
				draw();
			}
		});
		
		
		
		saveroutebtn.setOnAction(e -> {
			try {
				mazesolution.save("testfile");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		
		
		loadroutebtn.setOnAction(e -> {
			try {
				HashMap<Maze, RouteFinder> mapdata = mazesolution.loader("testfile");
				maze = (Maze) mapdata.keySet().toArray()[0];
				mazesolution = mapdata.get(maze);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			draw();
		});
		
		
		
		
		scene = new Scene(buttoncontainer, 500, 500);
        stage.setScene(scene);
        stage.show();
    }
	
	public void draw() {
        Collections.reverse(maze.getTiles());
        mazedisplay.getChildren().clear();
        for (int j=0; j<maze.getTiles().size(); j++) {
            for (int i=0; i<maze.getTiles().get(0).size(); i++) {
                if(maze.getTiles().get(j).get(i).getType() == Type.WALL) {
                    ImageView wallimg = new ImageView();
                    wallimg.setImage(wallsrc);
                    wallimg.setFitWidth(24);
                    wallimg.setFitHeight(24);
                    mazedisplay.add(wallimg, 5+i, j);
                    
                } 
                if (maze.getTiles().get(j).get(i).getCol() == true && mazesolution.isFinished() == true) {
                	ImageView exitimg = new ImageView();
                    exitimg.setImage(pathsrc);
                    exitimg.setFitWidth(24);
                    exitimg.setFitHeight(24);
                    mazedisplay.add(exitimg, 5+i, j);
                }
                if((maze.getTiles().get(j).get(i).getType() ==Type.CORRIDOR || maze.getTiles().get(j).get(i).getType() == Type.ENTRANCE) && maze.getTiles().get(j).get(i).getCol() == false){
                    ImageView pathimg = new ImageView();
                    pathimg.setImage(pathsrc);
                    pathimg.setFitWidth(24);
                    pathimg.setFitHeight(24);
                    mazedisplay.add(pathimg, 5+i, j);
                }
                if(maze.getTiles().get(j).get(i).getCol() == true){
                    ImageView correctimg = new ImageView();
                    correctimg.setImage(correctsrc);
                    correctimg.setFitWidth(24);
                    correctimg.setFitHeight(24);
                    mazedisplay.add(correctimg, 5+i, j);
                }
                if(maze.getTiles().get(j).get(i).getType() == Type.EXIT){
                    ImageView exitimg = new ImageView();
                    exitimg.setImage(exitsrc);
                    exitimg.setFitWidth(24);
                    exitimg.setFitHeight(24);
                    mazedisplay.add(exitimg, 5+i, j);
                }
                
            }
        }
        Collections.reverse(maze.getTiles());
}
}
