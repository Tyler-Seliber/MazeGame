/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import maze.ui.MazeViewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class SimpleMazeGame
{
	/**
	 * Creates a small maze.
	 */
	public static Maze createMaze()
	{
		
		Maze maze = new Maze();
		// Create 2 rooms
		Room room0 = new Room(0);
		Room room1 = new Room(1);
		maze.addRoom(room0);
		maze.addRoom(room1);

		// Create a door between room0 and room1
		Door door0 = new Door(room0, room1);

		room0.setSide(Direction.North, new Wall());
		room0.setSide(Direction.East, new Wall());
		room0.setSide(Direction.West, room1);
		room0.setSide(Direction.South, new Wall());

		room1.setSide(Direction.East, door0);
		room1.setSide(Direction.West, new Wall());
		room1.setSide(Direction.South, new Wall());
		room1.setSide(Direction.North, new Wall());

		maze.setCurrentRoom(0);

		return maze;
		

	}

	public static Maze loadMaze(final String path) {
		// Create ArrayLists to store rooms and doors from each line in the input file
		ArrayList<String[]> inputs = new ArrayList<>();
		Maze maze = new Maze();
		try {
			File mazeFile = new File(path);
			Scanner myReader = new Scanner(mazeFile);
			// Read the file line by line
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				// Split the line into an array of strings in the format:
				// <Room/Door> <room no./door no.> < North> <South> <East> <West>
				String[] dataArray = data.split(" ");
				inputs.add(dataArray);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		// Create rooms/doors
		Room[] rooms = new Room[inputs.size()];
		Door[] doors = new Door[inputs.size()];
		for (String[] input : inputs) {
			if (input[0].equals("room")) {
				// Create a room
				int roomNumber = Integer.parseInt(input[1]);
				Room room = new Room(roomNumber);
				rooms[roomNumber] = room;

			} else if (input[0].equals("door")) {
				// Create a door
				Door door = new Door(rooms[Integer.parseInt(input[2])], rooms[Integer.parseInt(input[3])]);
				if (input[4].equals("open")) {
					door.setOpen(true);
				}

				String doorNumberStr = input[1].substring(1); // Remove the 'd' from the door number
				int doorNumber = Integer.parseInt(doorNumberStr);
				doors[doorNumber] = door;

			}
		}
		// Set properties of rooms
		for (String[] input : inputs) {
			if (input[0].equals("room")) {
				int roomNumber = Integer.parseInt(input[1]);
				Room room = rooms[roomNumber];
				// Set the sides of the room
				String north, south, east, west;

				// North side
				north = input[2];
				MapSite toNorth;
				if (north.equals("wall")) {
					toNorth = new Wall();
				} else if (north.startsWith("d")) { // door
					int doorNumber = Integer.parseInt(north.substring(1));
					toNorth = doors[doorNumber];
				} else { // room
					int roomNumberNorth = Integer.parseInt(north);
					toNorth = rooms[roomNumberNorth];
				}
				room.setSide(Direction.North, toNorth);

				// South side
				south = input[3];
				MapSite toSouth;
				if (south.equals("wall")) {
					toSouth = new Wall();
				} else if (south.startsWith("d")) { // door
					int doorNumber = Integer.parseInt(south.substring(1));
					toSouth = doors[doorNumber];
				} else { // room
					int roomNumberSouth = Integer.parseInt(south);
					toSouth = rooms[roomNumberSouth];
				}
				room.setSide(Direction.South, toSouth);

				// East side
				east = input[4];
				MapSite toEast;
				if (east.equals("wall")) {
					toEast = new Wall();
				} else if (east.startsWith("d")) { // door
					int doorNumber = Integer.parseInt(east.substring(1));
					toEast = doors[doorNumber];
				} else { // room
					int roomNumberEast = Integer.parseInt(east);
					toEast = rooms[roomNumberEast];
				}
				room.setSide(Direction.East, toEast);

				// West side
				west = input[5];
				MapSite toWest;
				if (west.equals("wall")) {
					toWest = new Wall();
				} else if (west.startsWith("d")) { // door
					int doorNumber = Integer.parseInt(west.substring(1));
					toWest = doors[doorNumber];
				} else { // room
					int roomNumberWest = Integer.parseInt(west);
					toWest = rooms[roomNumberWest];
				}
				room.setSide(Direction.West, toWest);

				maze.addRoom(room);
			}
		}

		maze.setCurrentRoom(rooms[0]);
		return maze;
	}

	public static void main(String[] args)
	{
		Maze maze;
		if ((args.length == 0)) {
			maze = createMaze();
		} else {
			maze = loadMaze(args[0]);
		}
	    MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
}
