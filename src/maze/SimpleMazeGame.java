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
		room0.setSide(Direction.West, door0);
		room0.setSide(Direction.South, new Wall());

		room1.setSide(Direction.East, door0);
		room1.setSide(Direction.West, new Wall());
		room1.setSide(Direction.South, new Wall());
		room1.setSide(Direction.North, new Wall());

		maze.setCurrentRoom(0);

		return maze;
		

	}

	public  Maze loadMaze(final String path)
	{
		Maze maze = new Maze();
		System.out.println("Please load a maze from the file!");
		return maze;
	}

	public static void main(String[] args)
	{
		Maze maze = createMaze();
	    MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
}
