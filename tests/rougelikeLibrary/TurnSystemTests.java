package rougelikeLibrary;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.*;

import rougelikeLibrary.Position.CardinalDirection;

public class TurnSystemTests {
	@Test
	public void testTurnCharacterPlacedInMap() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Enemy e = new Enemy(0, 0, 0, 5, 5, new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new Position(0,0), new RoomSpace(3,3));
		r.addEnemy(new Position(5,5), e);
		
		assertTrue(r.getFromPosition(new Position(5,5)).size() > 0);
	}
	@Test
	public void testTurnSystemMove() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Position startLocation = new Position(5,5);
		Character c = new Character(0, 0, 0, startLocation.getLocation(), new TurnSystem(new EnemyAI(1)));
		HashMap<Position, Object> room = new HashMap<Position, Object>();
		room.put(c.getPosition(), c);
		
		assertNotNull(room.get(c.getPosition()));
		ts.move(c, new Position(4,5), room);
		assertNotEquals(startLocation, c.getPosition());
		assertNull(room.get(startLocation));
		assertNotNull(room.get(c.getPosition()));
	}
	
	@Test
	public void testTurnSystemGetNewLocation() {
		Position location = new Position(5,5);
		Character c = new Character(0, 0, 0, location.getLocation(), new TurnSystem(new EnemyAI(1)));
		HashMap<Position, Object> room = new HashMap<Position, Object>();
		room.put(c.getPosition(), c);
		
		location = location.setX(5);
		location = location.setY(4);
		Position p = new Position(5,4); //TEMP;
		//Position p = ts.getNewLocation(c, ai.requestMove());
		assertEquals(location, p);
	}
	
	@Test
	public void testTurnSystemEnterDoor()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Player p = new Player(10, 0, 0, 0, 0, ts);
		Room r = new Room(new Position(0,0), new RoomSpace(3,3));
		r.setPlayer(p.getPosition(), p);
		r.addDoor(CardinalDirection.North);
		r.addDoor(CardinalDirection.West);
		
		assertTrue(p.startTurn(r));
	}
	
	@Test
	public void testTurnSystemPickUpItem()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Player p = new Player(10, 0, 0, 0, 0, ts);
		Room r = new Room(new Position(0,0), new RoomSpace(2,2));
		r.setPlayer(p.getPosition(), p);
		r.addItem(new Position(0,1), new Item("Boots of speed",25,Item.Effect.SPEED));
		p.startTurn(r);
		assertTrue(p.getInventory().getItems().size() > 0);
	}
	
	@Test
	public void testTurnSystemPlayerAttackEnemy()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Player p = new Player(10, 0, 1, 0, 0, ts);
		Room r = new Room(new Position(0,0), new RoomSpace(1,2));
		r.setPlayer(p.getPosition(), p);
		Enemy e = new Enemy(0,1,0,new TurnSystem(new EnemyAI(0)));
		r.addEnemy(new Position(0,1),e);
		p.startTurn(r);
		assertTrue(e.getHealth() < 100);
	}
	
	@Test
	public void testTurnSystemEnemyAttackPlayer()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Player p = new Player(0, 100, 1, new Position(0,0), ts);
		Room r = new Room(new Position(0,0), new RoomSpace(1,2));
		r.setPlayer(p.getPosition(), p);
		Enemy e = new Enemy(10,100,1,new TurnSystem(new EnemyAI(0)));
		r.addEnemy(new Position(0,1),e);
		e.startTurn(r);
		assertTrue(p.getHealth() < 100);
	}
	
	@Test
	public void testTurnSystemEnemyNOTAttackEnemy()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Enemy standStill = new Enemy(0, 100, 1, new Position(0,0), ts);
		Room r = new Room(new Position(0,0), new RoomSpace(1,2));
		Enemy attackingEnemy = new Enemy(10,100,1,new TurnSystem(new EnemyAI(0)));
		r.addEnemy(attackingEnemy.getPosition(),attackingEnemy);
		r.addEnemy(standStill.getPosition(), standStill);
		attackingEnemy.startTurn(r);
		assertTrue(standStill.getHealth() == 100);
	}

	
	
	@Test
	public void testTurnSystemDontEnterDoor()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Character c = new Character(1, 0, 0, 1, 1, ts);
		Room r = new Room(new Position(0,0), new RoomSpace(100,100));
		r.setPlayer(c.getPosition(), c);
		assertFalse(c.startTurn(r));
	}
	
	@Test
	public void testTurnOutOfBounds()
	{
		Character c = new Character(1, 0, 0, 5, 5, new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new Position(0,0), new RoomSpace(0,0));
		
		assertFalse(c.startTurn(r));
	}
}
