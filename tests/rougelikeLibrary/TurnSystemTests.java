package rougelikeLibrary;

import static org.junit.Assert.*;


import org.junit.*;

import rougelikeLibrary.Position.CardinalDirection;

public class TurnSystemTests {
	@Test
	public void testTurnCharacterPlacedInMap() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Enemy e = new Enemy(0, 0, 0, new Position(5,5), new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new Position(0,0), new RoomSpace(3,3));
		r.addEnemy(new Position(5,5), e);
		
		assertTrue(r.getFromPosition(new Position(5,5)).size() > 0);
	}
	@Test
	public void testTurnSystemMove() {
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Position startLocation = new Position(2,2);
		Room r = new Room(new Position(0,0), new RoomSpace(5,5));
		Enemy e = new Enemy(1, 0, 0, startLocation, new TurnSystem(new EnemyAI(1)));
		r.addEnemy(e.getPosition(), e);
		e.startTurn(r);
		
		assertNotNull(r.existCharacter(r.getFromPosition(e.getPosition())));
		assertNotEquals(startLocation, e.getPosition());
	}
	
	@Test
	public void testTurnSystemEnterDoor()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Player p = new Player(10, 0, 0, new Position(0,0), ts);
		Room r = new Room(new Position(0,0), new RoomSpace(3,3));
		r.setPlayer(p.getPosition(), p);
		r.addDoor(CardinalDirection.North);
		r.addDoor(CardinalDirection.West);
		
		assertTrue(p.startTurn(r));
	}
	
	@Test
	public void testTurnSystemEnterDoorWithItemOver()
	{TurnSystem ts = new TurnSystem(new EnemyAI(2));
	Player p = new Player(10, 0, 0, new Position(0,0), ts);
	Room r = new Room(new Position(0,0), new RoomSpace(3,3));
	r.addItem(new Position(1,0), new Item("Boots",25,Item.Effect.SPEED));
	r.addItem(new Position(0,1), new Item("Boots",25,Item.Effect.SPEED));
	r.setPlayer(p.getPosition(), p);
	r.addDoor(CardinalDirection.North);
	r.addDoor(CardinalDirection.West);
	
	assertTrue(p.startTurn(r));
	assertTrue(p.getInventory().getItems().size() > 0);
		
	}
	
	@Test
	public void testTurnSystemPickUpItem()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Player p = new Player(10, 0, 0, new Position(0,0), ts);
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
		Player p = new Player(10, 0, 1, new Position(0,0), ts);
		Room r = new Room(new Position(0,0), new RoomSpace(1,2));
		r.setPlayer(p.getPosition(), p);
		Enemy e = new Enemy(0,1,0, new Position(0,0), new TurnSystem(new EnemyAI(0)));
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
		Enemy e = new Enemy(10,100,1, new Position(0,0), new TurnSystem(new EnemyAI(0)));
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
		Enemy attackingEnemy = new Enemy(10,100,1, new Position(0,1),new TurnSystem(new EnemyAI(0)));
		r.addEnemy(attackingEnemy.getPosition(),attackingEnemy);
		r.addEnemy(standStill.getPosition(), standStill);
		attackingEnemy.startTurn(r);
		assertTrue(standStill.getHealth() == 100);
	}

	
	
	@Test
	public void testTurnSystemDontEnterDoor()
	{
		TurnSystem ts = new TurnSystem(new EnemyAI(2));
		Enemy e = new Enemy(1, 0, 0, new Position(1,1),  ts);
		Room r = new Room(new Position(0,0), new RoomSpace(100,100));
		r.addEnemy(e.getPosition(), e);
		
		assertFalse(e.startTurn(r));
	}
	
	@Test
	public void testTurnOutOfBounds()
	{
		Character c = new Character(1, 0, 0, new Position(5,5), new TurnSystem(new EnemyAI(1)));
		Room r = new Room(new Position(0,0), new RoomSpace(0,0));
		
		assertFalse(c.startTurn(r));
	}
}
