import java.awt.Color;
import java.util.Random;

import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.FontStyle;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import tester.Tester;

// constants used for the game
interface IConstants {
  int SCREEN_HEIGHT = 300;
  int SCREEN_WIDTH = 500;
  int FONT_SIZE = 13;
  Color FONT_COLOR = Color.BLACK;
  Color SHIP_COLOR = Color.CYAN;
  Color BULLET_COLOR = Color.PINK;
  int BULLET_SPEED = 8;
  int SHIP_SPEED = BULLET_SPEED / 2;
  int BULLET_RADIUS = 2;
  int BULLET_RADIUS_INCREASE = 2;
  int MAX_BULLET_RADIUS = 10;
  int SHIP_RADIUS = SCREEN_HEIGHT / 30;
  int TOP_SHIP_SPAWN = SCREEN_HEIGHT / 7;
  int BOTTOM_SHIP_SPAWN = TOP_SHIP_SPAWN * 6;
}

// to represent a list of ships
interface ILoShip {
  // to draw a list of ships on the screen
  WorldScene draw(WorldScene last);

  // to move the position of each ship in the list
  ILoShip move();

  // returns a list of ships with new spawned ships
  ILoShip shipSpawn();

  // removes ship from list, if it exists
  ILoShip remove(Ship s);

  // determines if bullet hit any ships in list
  boolean hitAnyShips(Bullet b);

  // returns list of ships that were involved in collision
  ILoShip collision(ILoBullet bullets, ILoShip acc);

  // removes all ships from this list that are in paramter list
  ILoShip removeAll(ILoShip ships);

  // returns length of list
  int length();

  // returns list of ships that are off the screen
  ILoShip off(ILoShip acc);
}

// to represent an empty list of ships
class MtLoShip implements ILoShip {
  MtLoShip() {
  }

  /*
   * TEMPLATE
   * FIELDS:
   * none
   * 
   * METHODS:
   * ... this.draw(WorldScene) ... -- WorldScene
   * ... this.move() ... -- ILoShip
   * ... this.shipSpawn() ... -- ILoShip
   * ... this.remove(Ship) ... -- ILoShip
   * ... this.hitAnyShips(Bullet) ... -- boolean
   * ... this.collision(ILoBullet, ILoShip) ... -- ILoShip
   * ... this.removeAll(ILoShip) ... -- ILoShip
   * ... this.length() ... -- int
   * ... this.off(ILoShip) ... -- ILoShip
   * 
   */
  // nothing to draw, return scene
  public WorldScene draw(WorldScene last) {
    return last;
  }

  // nothing to move, return empty
  public ILoShip move() {
    return this;
  }

  // spawns random ship and adds it to list
  public ILoShip shipSpawn() {
    int forwardRand = new Random().nextInt(2);
    int xStart = forwardRand * IConstants.SCREEN_WIDTH;
    int yStart = new Random().nextInt(5 * IConstants.SCREEN_HEIGHT / 7) + IConstants.TOP_SHIP_SPAWN;
    if (forwardRand == 0) {
      forwardRand = -1;
    }
    Ship s = new Ship(forwardRand, xStart, yStart, IConstants.SHIP_COLOR, IConstants.SHIP_RADIUS,
        IConstants.SHIP_SPEED);
    return new ConsLoShip(s, this);
  }

  // nothing to remove, return empty
  public ILoShip remove(Ship s) {
    return this;
  }

  // can't hit any ships in empty list, return false
  public boolean hitAnyShips(Bullet bullet) {
    return false;
  }

  // no collisions possible, return acc
  public ILoShip collision(ILoBullet bullets, ILoShip acc) {
    return acc;
  }

  // list is empty, nothing to remove
  public ILoShip removeAll(ILoShip ships) {
    return ships;
  }

  // length of empty list is 0
  public int length() {
    return 0;
  }

  // no ships off screen in empty list, return acc
  public ILoShip off(ILoShip acc) {
    return acc;
  }
}

// to represent a constructed list of ships
class ConsLoShip implements ILoShip {
  Ship first;
  ILoShip rest;

  ConsLoShip(Ship first, ILoShip rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE
   * FIELDS:
   * ... this.first ... -- Ship
   * ... this.rest ... -- ILoShip
   * 
   * METHODS:
   * ... this.draw(WorldScene) ... -- WorldScene
   * ... this.move() ... -- ILoShip
   * ... this.shipSpawn() ... -- ILoShip
   * ... this.remove(Ship) ... -- ILoShip
   * ... this.hitAnyShips(Bullet) ... -- boolean
   * ... this.collision(ILoBullet, ILoShip) ... -- ILoShip
   * ... this.removeAll(ILoShip) ... -- ILoShip
   * ... this.length() ... -- int
   * ... this.off(ILoShip) ... -- ILoShip
   * 
   * METHODS of FIELDS:
   * all above methods are valid for this.rest
   * ... this.first.offScreen ... -- boolean
   * ... this.first.sameGameObject(AGameObject) ... -- boolean
   * ... this.first.draw(WorldScene) ... -- WorldScene
   * ... this.first.move() ... -- Ship
   * ... this.first.hitObject(AGameObject) ... -- boolean
   */

  // to draw ships
  public WorldScene draw(WorldScene last) {
    //
    return this.rest.draw(this.first.draw(last));
  }

  // to move ships
  public ILoShip move() {
    return new ConsLoShip(this.first.move(), this.rest.move());
  }

  // to spawn ships
  public ILoShip shipSpawn() {
    int forwardRand = new Random().nextInt(2);
    int xStart = forwardRand * IConstants.SCREEN_WIDTH;
    int yStart = new Random().nextInt(5 * IConstants.SCREEN_HEIGHT / 7) + IConstants.TOP_SHIP_SPAWN;
    if (forwardRand == 0) {
      forwardRand = -1;
    }
    Ship s = new Ship(forwardRand, xStart, yStart, IConstants.SHIP_COLOR, IConstants.SHIP_RADIUS,
        IConstants.SHIP_SPEED);
    return new ConsLoShip(s, this);
  }

  // to remove specified ship from list
  public ILoShip remove(Ship that) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Ship
     * ... this.rest ... -- ILoShip
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoShip
     * ... this.shipSpawn() ... -- ILoShip
     * ... this.remove(Ship) ... -- ILoShip
     * ... this.hitAnyShips(Bullet) ... -- boolean
     * ... this.collision(ILoBullet, ILoShip) ... -- ILoShip
     * ... this.removeAll(ILoShip) ... -- ILoShip
     * ... this.length() ... -- int
     * ... this.off(ILoShip) ... -- ILoShip
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Ship
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * 
     * FIELDS of Parameters:
     * ... that.xPosn ... -- int
     * ... that.yPosn ... -- int
     * ... that.color ... -- Color
     * ... that.radius ... -- int
     * ... that.forward ... -- int
     * ... that.velocity ... -- int
     * 
     * METHODS of Parameters:
     * ... same as METHODS of FIELDS for this.first ...
     */
    if (this.first.sameGameObject(that)) {
      return this.rest.remove(that);
    }
    else {
      return new ConsLoShip(this.first, this.rest.remove(that));
    }
  }

  // returns true if the bullet hit any ships
  public boolean hitAnyShips(Bullet bullet) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Ship
     * ... this.rest ... -- ILoShip
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoShip
     * ... this.shipSpawn() ... -- ILoShip
     * ... this.remove(Ship) ... -- ILoShip
     * ... this.hitAnyShips(Bullet) ... -- boolean
     * ... this.collision(ILoBullet, ILoShip) ... -- ILoShip
     * ... this.removeAll(ILoShip) ... -- ILoShip
     * ... this.length() ... -- int
     * ... this.off(ILoShip) ... -- ILoShip
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Ship
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * 
     * FIELDS of Parameters:
     * ... bullet.xPosn ... -- int
     * ... bullet.yPosn ... -- int
     * ... bullet.color ... -- Color
     * ... bullet.radius ... -- int
     * ... bullet.xVelocity ... -- double
     * ... bullet.yVelocity ... -- double
     * ... bullet.mult ... -- int
     * 
     * METHODS of Parameters:
     * ... same as METHODS of FIELDS plus ...
     * ... bullet.move() ... -- Bullet
     * ... bullet.explode() ... -- ILoBullet
     * ... bullet.getMult() ... -- int
     */
    return this.first.hitObject(bullet) || this.rest.hitAnyShips(bullet);
  }

  // returns list of ships that collided with bullets
  public ILoShip collision(ILoBullet bullets, ILoShip acc) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Ship
     * ... this.rest ... -- ILoShip
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoShip
     * ... this.shipSpawn() ... -- ILoShip
     * ... this.remove(Ship) ... -- ILoShip
     * ... this.hitAnyShips(Bullet) ... -- boolean
     * ... this.collision(ILoBullet, ILoShip) ... -- ILoShip
     * ... this.removeAll(ILoShip) ... -- ILoShip
     * ... this.length() ... -- int
     * ... this.off(ILoShip) ... -- ILoShip
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Ship
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * 
     * FIELDS of Parameters:
     * ... same as FIELDS above for acc ...
     * ... bullets.first ... -- Bullet
     * ... bullets.rest ... -- ILoBullet
     * 
     * METHODS of Parameters:
     * ... same as METHODS for acc ...
     * ... same as METHODS for bullets plus ...
     * ... bullets.hitAnyBullets(Ship) ... -- boolean
     * ... bullets.remove(Bullet) ... -- ILoBullet
     * ... bullets.bulletsOffScreen ... -- boolean
     * ... bullets.removeAll(ILoBullet) ... -- ILoBullet
     * ... bullets.explodeAll() ... -- ILoBullet
     * ... bullets.append(ILoBullet) ... -- ILoBullet
     * ... bullets.off(ILoBullet) ... -- ILoBullet
     */
    if (bullets.hitAnyBullets(this.first)) {
      return this.rest.collision(bullets, new ConsLoShip(this.first, acc));
    }
    else {
      return this.rest.collision(bullets, acc);
    }
  }

  // to remove all ships
  public ILoShip removeAll(ILoShip ships) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Ship
     * ... this.rest ... -- ILoShip
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoShip
     * ... this.shipSpawn() ... -- ILoShip
     * ... this.remove(Ship) ... -- ILoShip
     * ... this.hitAnyShips(Bullet) ... -- boolean
     * ... this.collision(ILoBullet, ILoShip) ... -- ILoShip
     * ... this.removeAll(ILoShip) ... -- ILoShip
     * ... this.length() ... -- int
     * ... this.off(ILoShip) ... -- ILoShip
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Ship
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * 
     * FIELDS of Parameters:
     * ... same as FIELDS ...
     * 
     * METHODS of Parameters:
     * ... same as METHODS ...
     */
    return this.rest.removeAll(ships.remove(this.first));
  }

  // returns length of list
  public int length() {
    return 1 + this.rest.length();
  }

  // returns list of ships off screen
  // ACCUMULATOR: current list of ships off screen
  public ILoShip off(ILoShip acc) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Ship
     * ... this.rest ... -- ILoShip
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoShip
     * ... this.shipSpawn() ... -- ILoShip
     * ... this.remove(Ship) ... -- ILoShip
     * ... this.hitAnyShips(Bullet) ... -- boolean
     * ... this.collision(ILoBullet, ILoShip) ... -- ILoShip
     * ... this.removeAll(ILoShip) ... -- ILoShip
     * ... this.length() ... -- int
     * ... this.off(ILoShip) ... -- ILoShip
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Ship
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * 
     * FIELDS of Parameters:
     * ... same as FIELDS ...
     * 
     * METHODS of Parameters:
     * ... same as METHODS ...
     */
    if (this.first.offScreen()) {
      return new ConsLoShip(this.first, this.rest.off(acc));
    }
    else {
      return acc;
    }
  }
}

// to represent a list of bullets
interface ILoBullet {
  // draws all bullets on screen
  WorldScene draw(WorldScene last);

  // returns true if ship hit any bullets
  boolean hitAnyBullets(Ship ship);

  // moves position of all bullets in list
  ILoBullet move();

  // removes bullet from list
  ILoBullet remove(Bullet that);

  // returns true if all bullets are off screen (no bullets on screen)
  boolean bulletsOffScreen();

  // returns list of all bullets that were in collision
  ILoBullet collision(ILoShip ships, ILoBullet bullets);

  // removes all elements from this list that are in bullets
  ILoBullet removeAll(ILoBullet bullets);

  // returns list of all bullets exploded in list
  ILoBullet explodeAll();

  // appends two lists together
  ILoBullet append(ILoBullet other);

  // returns a list of all bullets off screen
  ILoBullet off(ILoBullet acc);
}

// to represent an empty list of bullets
class MtLoBullet implements ILoBullet {
  MtLoBullet() {
  }

  // no bullets on screen so empty scene
  public WorldScene draw(WorldScene last) {
    return last;
  }

  // no bullets to move
  public ILoBullet move() {
    return this;
  }

  // no bullets to remove
  public ILoBullet remove(Bullet that) {
    return this;
  }

  // no bullets on screen in empty list, so return true
  public boolean bulletsOffScreen() {
    return true;
  }

  // can't hit any bullets in empty list, return false
  public boolean hitAnyBullets(Ship ship) {
    return false;
  }

  // no bullets to collide with, return bullets
  public ILoBullet collision(ILoShip ships, ILoBullet bullets) {
    return bullets;
  }

  // removes all bullets from list
  public ILoBullet removeAll(ILoBullet bullets) {
    return bullets;
  }

  // exploding empty list of bullets is empty list
  public ILoBullet explodeAll() {
    return this;
  }

  // appends two lists
  public ILoBullet append(ILoBullet other) {
    return other;
  }

  // no bullets off screen in empty list
  public ILoBullet off(ILoBullet acc) {
    return acc;
  }
}

// to represent a constructed list of bullets
class ConsLoBullet implements ILoBullet {
  Bullet first;
  ILoBullet rest;

  ConsLoBullet(Bullet first, ILoBullet rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE
   * FIELDS:
   * ... this.first ... -- Bullet
   * ... this.rest ... -- ILoBullet
   * 
   * METHODS:
   * ... this.draw(WorldScene) ... -- WorldScene
   * ... this.move() ... -- ILoBullet
   * ... this.remove(Bullet) ... -- ILoBullet
   * ... this.bulletsOffScreen() ... -- boolean
   * ... this.hitAnyBullets(Ship) ... -- boolean
   * ... this.collision(ILoShip, ILoBullet) ... -- ILoBullet
   * ... this.removeAll(ILoBullet) ... -- ILoBullet
   * ... this.explodeAll() ... -- ILoBullet
   * ... this.append(ILoBullet) ... -- ILoBullet
   * ... this.off(ILoBullet) ... -- ILoBullet
   * 
   * METHODS of FIELDS:
   * all above methods are valid for this.rest
   * ... this.first.offScreen ... -- boolean
   * ... this.first.sameGameObject(AGameObject) ... -- boolean
   * ... this.first.draw(WorldScene) ... -- WorldScene
   * ... this.first.move() ... -- Bullet
   * ... this.first.hitObject(AGameObject) ... -- boolean
   * ... this.explode() ... -- ILoBullet
   * ... this.getMult() ... -- int
   */

  // to draw bullets
  public WorldScene draw(WorldScene last) {
    return this.rest.draw(this.first.draw(last));
  }

  // to move bullets
  public ILoBullet move() {
    return new ConsLoBullet(this.first.move(), this.rest.move());
  }

  // to remove bullets
  public ILoBullet remove(Bullet that) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Bullet
     * ... this.rest ... -- ILoBullet
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoBullet
     * ... this.remove(Bullet) ... -- ILoBullet
     * ... this.bulletsOffScreen() ... -- boolean
     * ... this.hitAnyBullets(Ship) ... -- boolean
     * ... this.collision(ILoShip, ILoBullet) ... -- ILoBullet
     * ... this.removeAll(ILoBullet) ... -- ILoBullet
     * ... this.explodeAll() ... -- ILoBullet
     * ... this.append(ILoBullet) ... -- ILoBullet
     * ... this.off(ILoBullet) ... -- ILoBullet
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Bullet
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * ... this.explode() ... -- ILoBullet
     * ... this.getMult() ... -- int
     * 
     * FIELDS of Parameters:
     * ... this.xPosn ... -- int
     * ... this.yPosn ... -- int
     * ... this.color ... -- Color
     * ... this.radius ... -- int
     * ... this.xVelocity ... -- double
     * ... this.yVelocity ... -- double
     * ... this.mult ... -- int
     * 
     * METHODS of Parameters:
     * ... same as METHODS of FIELDS for this.first ...
     */
    if (this.first.sameGameObject(that)) {
      return this.rest.remove(that);
    }
    else {
      return new ConsLoBullet(this.first, this.rest.remove(that));
    }
  }

  // returns true if all bullets are off screen
  public boolean bulletsOffScreen() {
    return this.first.offScreen() && this.rest.bulletsOffScreen();
  }

  // returns true if ship has hit any bullet in list
  public boolean hitAnyBullets(Ship ship) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Bullet
     * ... this.rest ... -- ILoBullet
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoBullet
     * ... this.remove(Bullet) ... -- ILoBullet
     * ... this.bulletsOffScreen() ... -- boolean
     * ... this.hitAnyBullets(Ship) ... -- boolean
     * ... this.collision(ILoShip, ILoBullet) ... -- ILoBullet
     * ... this.removeAll(ILoBullet) ... -- ILoBullet
     * ... this.explodeAll() ... -- ILoBullet
     * ... this.append(ILoBullet) ... -- ILoBullet
     * ... this.off(ILoBullet) ... -- ILoBullet
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Bullet
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * ... this.explode() ... -- ILoBullet
     * ... this.getMult() ... -- int
     * 
     * FIELDS of Parameters:
     * ... ship.xPosn ... -- int
     * ... ship.yPosn ... -- int
     * ... ship.color ... -- Color
     * ... ship.radius ... -- int
     * ... ship.forward ... -- int
     * ... ship.velocity ... -- int
     * 
     * METHODS of Parameters:
     * ... ship.offScreen ... -- boolean
     * ... ship.sameGameObject(AGameObject) ... -- boolean
     * ... ship.draw(WorldScene) ... -- WorldScene
     * ... ship.move() ... -- Ship
     * ... ship.hitObject(AGameObject) ... -- boolean
     */
    return this.first.hitObject(ship) || this.rest.hitAnyBullets(ship);
  }

  // returns list of bullets that were part of a collision
  // ACCUMULATOR = running list of bullets
  public ILoBullet collision(ILoShip ships, ILoBullet acc) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Bullet
     * ... this.rest ... -- ILoBullet
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoBullet
     * ... this.remove(Bullet) ... -- ILoBullet
     * ... this.bulletsOffScreen() ... -- boolean
     * ... this.hitAnyBullets(Ship) ... -- boolean
     * ... this.collision(ILoShip, ILoBullet) ... -- ILoBullet
     * ... this.removeAll(ILoBullet) ... -- ILoBullet
     * ... this.explodeAll() ... -- ILoBullet
     * ... this.append(ILoBullet) ... -- ILoBullet
     * ... this.off(ILoBullet) ... -- ILoBullet
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Bullet
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * ... this.explode() ... -- ILoBullet
     * ... this.getMult() ... -- int
     * 
     * FIELDS of Parameters:
     * ... same as FIELDS above for acc ...
     * ... ships.first ... -- Ship
     * ... ships.rest ... -- ILoShip
     * 
     * METHODS of Parameters:
     * ... same as METHODS for acc ...
     * ... same as METHODS for ships plus ...
     * ... ships.hitAnyShips(Bullet) ... -- boolean
     * ... ships.collision(ILoBullet, ILoShip) ... -- ILoShip
     * ... ships.remove(Ship) ... -- ILoShip
     * ... ships.removeAll(ILoShip) ... -- ILoShip
     * ... ships.length() ... -- int
     * ... ships.off() ... -- ILoShip
     */
    if (ships.hitAnyShips(this.first)) {
      return this.rest.collision(ships, new ConsLoBullet(this.first, acc));
    }
    else {
      return this.rest.collision(ships, acc);
    }
  }

  // removes all bullets from list
  public ILoBullet removeAll(ILoBullet bullets) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Bullet
     * ... this.rest ... -- ILoBullet
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoBullet
     * ... this.remove(Bullet) ... -- ILoBullet
     * ... this.bulletsOffScreen() ... -- boolean
     * ... this.hitAnyBullets(Ship) ... -- boolean
     * ... this.collision(ILoShip, ILoBullet) ... -- ILoBullet
     * ... this.removeAll(ILoBullet) ... -- ILoBullet
     * ... this.explodeAll() ... -- ILoBullet
     * ... this.append(ILoBullet) ... -- ILoBullet
     * ... this.off(ILoBullet) ... -- ILoBullet
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Bullet
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * ... this.explode() ... -- ILoBullet
     * ... this.getMult() ... -- int
     * 
     * FIELDS of Parameters:
     * ... same as FIELDS ...
     * 
     * METHODS of Parameters:
     * ... same as METHODS ...
     */
    return this.rest.removeAll(bullets.remove(this.first));
  }

  // returns list of exploded bullets
  public ILoBullet explodeAll() {
    ILoBullet explodedFirst = this.first.explode(this.first.getMult());
    return explodedFirst.append(this.rest.explodeAll());
  }

  // appends other list to this list
  public ILoBullet append(ILoBullet other) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Bullet
     * ... this.rest ... -- ILoBullet
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoBullet
     * ... this.remove(Bullet) ... -- ILoBullet
     * ... this.bulletsOffScreen() ... -- boolean
     * ... this.hitAnyBullets(Ship) ... -- boolean
     * ... this.collision(ILoShip, ILoBullet) ... -- ILoBullet
     * ... this.removeAll(ILoBullet) ... -- ILoBullet
     * ... this.explodeAll() ... -- ILoBullet
     * ... this.append(ILoBullet) ... -- ILoBullet
     * ... this.off(ILoBullet) ... -- ILoBullet
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Bullet
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * ... this.explode() ... -- ILoBullet
     * ... this.getMult() ... -- int
     * 
     * FIELDS of Parameters:
     * ... same as FIELDS ...
     * 
     * METHODS of Parameters:
     * ... same as METHODS ...
     */
    return new ConsLoBullet(this.first, this.rest.append(other));
  }

  // returns list of bullets that are off screen
  // ACCUMULATOR: current list of bullets off screen
  public ILoBullet off(ILoBullet acc) {
    /*
     * TEMPLATE
     * FIELDS:
     * ... this.first ... -- Bullet
     * ... this.rest ... -- ILoBullet
     * 
     * METHODS:
     * ... this.draw(WorldScene) ... -- WorldScene
     * ... this.move() ... -- ILoBullet
     * ... this.remove(Bullet) ... -- ILoBullet
     * ... this.bulletsOffScreen() ... -- boolean
     * ... this.hitAnyBullets(Ship) ... -- boolean
     * ... this.collision(ILoShip, ILoBullet) ... -- ILoBullet
     * ... this.removeAll(ILoBullet) ... -- ILoBullet
     * ... this.explodeAll() ... -- ILoBullet
     * ... this.append(ILoBullet) ... -- ILoBullet
     * ... this.off(ILoBullet) ... -- ILoBullet
     * 
     * METHODS of FIELDS:
     * all above methods are valid for this.rest
     * ... this.first.offScreen ... -- boolean
     * ... this.first.sameGameObject(AGameObject) ... -- boolean
     * ... this.first.draw(WorldScene) ... -- WorldScene
     * ... this.first.move() ... -- Bullet
     * ... this.first.hitObject(AGameObject) ... -- boolean
     * ... this.explode() ... -- ILoBullet
     * ... this.getMult() ... -- int
     * 
     * FIELDS of Parameters:
     * ... same as FIELDS ...
     * 
     * METHODS of Parameters:
     * ... same as METHODS ...
     */
    if (this.first.offScreen()) {
      return new ConsLoBullet(this.first, this.rest.off(acc));
    }
    else {
      return acc;
    }
  }
}

// to represent a game object (ship or bullet)
abstract class AGameObject {
  int xPosn;
  int yPosn;
  Color color;
  int radius;

  AGameObject(int xPosn, int yPosn, Color color, int radius) {
    this.xPosn = xPosn;
    this.yPosn = yPosn;
    this.color = color;
    this.radius = radius;
  }

  /*
   * TEMPLATE
   * FIELDS:
   * ... this.xPosn ... -- int
   * ... this.yPosn ... -- int
   * ... this.color ... -- Color
   * ... this.radius ... -- int
   * 
   * METHODS:
   * ... this.offScreen() ... -- boolean
   * ... this.sameGameObject(AGameObject) ... -- boolean
   * ... this.draw(WorldScene) ... -- WorldScene
   * ... this.move() ... -- AGameObject
   * ... this.hitObject(AGameObject) ... -- boolean
   */

  // returns true if AGameObject is of the screen
  public boolean offScreen() {
    return this.xPosn < 0 || this.xPosn > IConstants.SCREEN_WIDTH || this.yPosn < 0
        || this.yPosn > IConstants.SCREEN_HEIGHT;
  }

  // returns true if this AGameObject is same as other AGameObject
  boolean sameGameObject(AGameObject other) {
    return this.xPosn == other.xPosn && this.yPosn == other.yPosn && this.color.equals(other.color)
        && this.radius == other.radius;
  }

  // draws an AGameObject
  WorldScene draw(WorldScene scene) {
    return scene.placeImageXY(new CircleImage(this.radius, "solid", this.color), this.xPosn,
        this.yPosn);
  }

  // moves position of AGameObject
  abstract AGameObject move();

  // determines if two objects have hit each other
  boolean hitObject(AGameObject other) {
    return Math.hypot(this.xPosn - other.xPosn, this.yPosn - other.yPosn) < this.radius
        + other.radius;
  }
}

// to represent a ship in the game
class Ship extends AGameObject {
  int forward;
  int velocity;

  Ship(int forward, int xPosn, int yPosn, Color color, int radius, int velocity) {
    super(xPosn, yPosn, IConstants.SHIP_COLOR, IConstants.SHIP_RADIUS);
    // forward - 1 = to the right, -1 to the left
    if (forward != 1 && forward != -1) {
      throw new IllegalArgumentException("Must be 1 or -1");
    }
    else {
      this.forward = forward;
    }
    this.velocity = -IConstants.SHIP_SPEED * forward;
  }

  /*
   * TEMPLATE
   * FIELDS:
   * ... this.xPosn ... -- int
   * ... this.yPosn ... -- int
   * ... this.color ... -- Color
   * ... this.radius ... -- int
   * ... this.forward ... -- int
   * ... this.velocity ... -- int
   * 
   * METHODS:
   * ... this.offScreen() ... -- boolean
   * ... this.sameGameObject(AGameObject) ... -- boolean
   * ... this.draw(WorldScene) ... -- WorldScene
   * ... this.move() ... -- Ship
   * ... this.hitObject(AGameObject) ... -- boolean
   */

  // moves ship's position
  Ship move() {
    return new Ship(this.forward, this.xPosn + this.velocity, this.yPosn, this.color, this.radius,
        this.velocity);
  }
}

// to represent a bullet in the game
class Bullet extends AGameObject {
  double xVelocity;
  double yVelocity;
  int mult;

  Bullet(int radius, int xPosn, int yPosn, Color color, double xVelocity, double yVelocity, 
      int mult) {
    super(xPosn, yPosn, IConstants.BULLET_COLOR, radius);

    if (radius > IConstants.MAX_BULLET_RADIUS) {
      this.radius = IConstants.MAX_BULLET_RADIUS;
    }
    else {
      this.radius = radius;
    }

    // make sure components of velocity add to total velocity
    if (Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2)
    - Math.pow(IConstants.BULLET_SPEED, 2) > .001) {
      throw new IllegalArgumentException("Speed of bullet must be " + IConstants.BULLET_SPEED);
    }
    else {
      this.xVelocity = xVelocity;
      this.yVelocity = yVelocity;
    }
    this.mult = mult;
  }

  /*
   * TEMPLATE
   * FIELDS:
   * ... this.xPosn ... -- int
   * ... this.yPosn ... -- int
   * ... this.color ... -- Color
   * ... this.radius ... -- int
   * ... this.xVelocity ... -- double
   * ... this.yVelocity ... -- double
   * 
   * METHODS:
   * ... this.offScreen() ... -- boolean
   * ... this.sameGameObject(AGameObject) ... -- boolean
   * ... this.draw(WorldScene) ... -- WorldScene
   * ... this.move() ... -- Bullet
   * ... this.hitObject(AGameObject) ... -- boolean
   * ... this.explode(int) ... -- ILoBullet
   * ... this.getMult() ... -- int
   */

  // moves bullets on screen
  Bullet move() {
    return new Bullet(this.radius, (int) (this.xPosn + this.xVelocity),
        (int) (this.yPosn - this.yVelocity), this.color, this.xVelocity, this.yVelocity, this.mult);
  }

  // on collision, bullet explodes into many bullets
  ILoBullet explode(int bullets) {
    if (bullets < 0) {
      return new MtLoBullet();
    }
    else {
      // calculate x velocity based on explosion number
      double angleInDeg = 360 * bullets / (1 + this.mult);
      double xVelocity = IConstants.BULLET_SPEED * Math.cos(Math.PI * angleInDeg / 180);
      double yVelocity = IConstants.BULLET_SPEED * Math.sin(Math.PI * angleInDeg / 180);
      return new ConsLoBullet(
          new Bullet(this.radius + IConstants.BULLET_RADIUS_INCREASE, this.xPosn, this.yPosn,
              IConstants.BULLET_COLOR, xVelocity, yVelocity, this.mult + 1),
          this.explode(bullets - 1));
    }
  }

  // returns multiplicity
  int getMult() {
    return this.mult;
  }
}

// to represent a game scene in the World
class WorldDraw extends World {
  int bulletCount;
  int shipsDestroyed;
  ILoShip ships;
  ILoBullet bullets;
  int shipSpawn;

  WorldDraw(int bulletCount, int shipsDestroyed, ILoShip ships, ILoBullet bullets, int shipSpawn) {
    this.bulletCount = bulletCount;
    this.shipsDestroyed = shipsDestroyed;
    this.ships = ships;
    this.bullets = bullets;
    this.shipSpawn = shipSpawn;
  }

  // constructor that takes in only bullets left
  WorldDraw(int bulletCount) {
    this(bulletCount, 0, new MtLoShip(), new MtLoBullet(), 0);

  }

  // moves bullets and ships on screen
  public WorldDraw move() {
    return new WorldDraw(this.bulletCount, this.shipsDestroyed, this.ships.move(),
        this.bullets.move(), this.shipSpawn);
  }

  // moves objects, spawns ships, removes off screen objects, then handles any
  // collisions
  public World onTick() {
    return this.move().spawnShips().handleOffScreen().handleCollision();
  }

  // creates empty scene but with stats on bottom left
  public WorldScene makeScene() {
    TextImage message = new TextImage("Bullets left: " + String.valueOf(this.bulletCount)
    + " ; Ships destroyed: " + String.valueOf(this.shipsDestroyed), IConstants.FONT_SIZE,
    IConstants.FONT_COLOR);

    WorldScene scene = this.getEmptyScene().placeImageXY(message, 100 + IConstants.FONT_SIZE,
        IConstants.SCREEN_HEIGHT - IConstants.FONT_SIZE).
        placeImageXY(new RectangleImage(10, 20, "solid", Color.BLACK), IConstants.SCREEN_WIDTH / 2, IConstants.SCREEN_HEIGHT - 10);
    return this.bullets.draw(this.ships.draw(scene));

  }

  // spawns ships if space bar was pressed
  public World onKeyReleased(String key) {
    Bullet b = new Bullet(IConstants.BULLET_RADIUS, IConstants.SCREEN_WIDTH / 2,
        IConstants.SCREEN_HEIGHT - IConstants.BULLET_RADIUS, IConstants.BULLET_COLOR, 0,
        IConstants.BULLET_SPEED, 1);
    ILoBullet newList = new ConsLoBullet(b, this.bullets);
    if (key.equals(" ") && this.bulletCount > 0) {
      return new WorldDraw(this.bulletCount - 1, this.shipsDestroyed, this.ships, newList,
          this.shipSpawn);
    }
    else {
      return this;
    }
  }

  // to spawn a random [1,3] amount of ships
  public WorldDraw spawnShips() {
    if (this.shipSpawn == 28) {
      int numShips = new Random().nextInt(3) + 1;
      if (numShips == 1) {
        return new WorldDraw(this.bulletCount, this.shipsDestroyed, this.ships.shipSpawn(),
            this.bullets, 0);
      }
      else if (numShips == 2) {
        return new WorldDraw(this.bulletCount, this.shipsDestroyed,
            this.ships.shipSpawn().shipSpawn(), this.bullets, 0);
      }
      else {
        return new WorldDraw(this.bulletCount, this.shipsDestroyed,
            this.ships.shipSpawn().shipSpawn().shipSpawn(), this.bullets, 0);
      }
    }
    else {
      return new WorldDraw(this.bulletCount, this.shipsDestroyed, this.ships, this.bullets,
          this.shipSpawn + 1);
    }
  }

  // to handle collisions between ships and bullets
  public WorldDraw handleCollision() {
    /// finds which ships have collided with a bullet in order to remove them
    ILoShip shipCollide = this.ships.collision(this.bullets, new MtLoShip());

    // number of ships destroyed
    int shipsShot = shipCollide.length();

    // finds which bullets have collided with ships in order to explode them
    ILoBullet bulletCollide = this.bullets.collision(this.ships, new MtLoBullet());

    // ships after hit ships are gone = RETURNED list of ships
    ILoShip finalShips = shipCollide.removeAll(this.ships);

    // bullets after colliding bullets are gone
    ILoBullet bulletsAfterRemoval = bulletCollide.removeAll(this.bullets);

    // bullets to be added after explosions
    ILoBullet explodedBullets = bulletCollide.explodeAll();

    // RETURNED list of bullets
    ILoBullet finalBullets = explodedBullets.append(bulletsAfterRemoval);

    return new WorldDraw(this.bulletCount, this.shipsDestroyed + shipsShot, finalShips,
        finalBullets, this.shipSpawn);
  }

  public WorldDraw handleOffScreen() {
    // list of all ships off screen
    ILoShip shipsOffScreen = this.ships.off(new MtLoShip());

    // list of all bullets off screen
    ILoBullet bulletsOffScreen = this.bullets.off(new MtLoBullet());

    // list of ships minus off screen ships
    ILoShip shipsFinal = shipsOffScreen.removeAll(this.ships);

    // list of bullets minus off screen bullets
    ILoBullet bulletsFinal = bulletsOffScreen.removeAll(this.bullets);

    return new WorldDraw(this.bulletCount, this.shipsDestroyed, shipsFinal, bulletsFinal,
        this.shipSpawn);
  }

  // determines if world ends and produces image accordingly
  public WorldEnd worldEnds() {
    TextImage gameOver = new TextImage("Game Over. Score = " + this.shipsDestroyed, 20,
        FontStyle.BOLD, Color.BLACK);
    return new WorldEnd(this.bulletCount == 0 && this.bullets.bulletsOffScreen(),
        this.getEmptyScene().placeImageXY(gameOver, IConstants.SCREEN_WIDTH / 2,
            IConstants.SCREEN_HEIGHT / 2));
  }
}

class RunWorld {
  RunWorld() {
  }

  Ship ship1 = new Ship(1, 5, 5, Color.CYAN, 10, 4);
  ILoShip mtShip = new MtLoShip();
  ILoShip shiplist1 = new ConsLoShip(ship1, mtShip);
  Random r = new Random(2);
  Ship ship2 = new Ship(1, 30, 30, Color.CYAN, 10, 4);
  Ship ship3 = new Ship(1, 600, 600, Color.CYAN, 10, 4);

  ILoShip shiplist2 = new ConsLoShip(ship2, shiplist1);
  ILoShip shiplist3 = new ConsLoShip(ship2, mtShip);
  ILoShip shiplist4 = new ConsLoShip(ship3, shiplist3);

  Bullet bullet1 = new Bullet(8, 5, 5, Color.PINK, 8, 0, 2);
  Bullet bullet2 = new Bullet(8, 200, 200, Color.PINK, 8, 0, 2);
  Bullet bullet3 = new Bullet(8, 600, 600, Color.PINK, 8, 0, 2);

  ILoBullet mtBullet = new MtLoBullet();
  ILoBullet bulletlist1 = new ConsLoBullet(this.bullet1, this.mtBullet);
  ILoBullet bulletlist2 = new ConsLoBullet(this.bullet2, this.bulletlist1);
  ILoBullet bulletlist3 = new ConsLoBullet(this.bullet2, this.mtBullet);
  ILoBullet bulletlist4 = new ConsLoBullet(this.bullet3, this.bulletlist3);
  ILoBullet bulletlist5 = new ConsLoBullet(this.bullet3, this.mtBullet);

  TextImage message = new TextImage("Hello", IConstants.FONT_SIZE, IConstants.FONT_COLOR);
  WorldScene scene = new WorldScene(500, 500).placeImageXY(message, 100, 100);

  boolean testDraw(Tester t) {
    // create my world. This will initialize everything
    WorldDraw world = new WorldDraw(10);
    return world.bigBang(IConstants.SCREEN_WIDTH, IConstants.SCREEN_HEIGHT, 0.036);
  }

  // test draw for ships
  boolean testDrawILoShips(Tester t) {
    return t.checkExpect(this.mtShip.draw(scene), scene)
        && t.checkExpect(this.shiplist1.draw(scene),
            scene.placeImageXY(new CircleImage(10, "solid", Color.CYAN), 5, 5));
  }

  // test move for ships
  boolean testMoveShips(Tester t) {
    return t.checkExpect(this.mtShip.move(), this.mtShip) && t.checkExpect(this.shiplist1.move(),
        new ConsLoShip(new Ship(1, 1, 5, Color.CYAN, 10, 4), this.mtShip));
  }

  // test remove for ships
  boolean testRemoveShips(Tester t) {
    return t.checkExpect(this.mtShip.remove(this.ship1), this.mtShip)
        && t.checkExpect(this.shiplist1.remove(this.ship1), this.mtShip)
        && t.checkExpect(this.shiplist1.remove(this.ship2), this.shiplist1);
  }

  // test hit any ships
  boolean testHitAnyShips(Tester t) {
    return t.checkExpect(this.mtShip.hitAnyShips(bullet1), false)
        && t.checkExpect(this.shiplist1.hitAnyShips(bullet1), true)
        && t.checkExpect(this.shiplist3.hitAnyShips(bullet1), false)
        && t.checkExpect(this.shiplist2.hitAnyShips(bullet1), true);

  }

  // test collisions for ships
  boolean testShipCollision(Tester t) {
    return t.checkExpect(this.mtShip.collision(bulletlist1, mtShip), mtShip)
        && t.checkExpect(this.shiplist1.collision(bulletlist1, mtShip), shiplist1)
        && t.checkExpect(this.shiplist3.collision(bulletlist1, mtShip), mtShip);

  }

  // test remove all for ships
  boolean testRemoveAll(Tester t) {
    return t.checkExpect(this.mtShip.removeAll(shiplist1), shiplist1)
        && t.checkExpect(this.shiplist1.removeAll(mtShip), mtShip)
        && t.checkExpect(this.shiplist1.removeAll(shiplist2), shiplist3);

  }

  // test length for ships
  boolean testLength(Tester t) {
    return t.checkExpect(this.mtShip.length(), 0) && t.checkExpect(this.shiplist1.length(), 1)
        && t.checkExpect(this.shiplist2.length(), 2);

  }

  // test off for ships
  boolean testOffScreen(Tester t) {
    return t.checkExpect(this.mtShip.off(mtShip), mtShip)
        && t.checkExpect(this.shiplist1.off(mtShip), mtShip)
        && t.checkExpect(this.shiplist2.off(mtShip), mtShip)
        && t.checkExpect(this.shiplist4.off(mtShip), new ConsLoShip(ship3, mtShip));

  }

  // test draw for bullets
  boolean testDrawILoBullets(Tester t) {
    return t.checkExpect(this.mtBullet.draw(scene), scene)
        && t.checkExpect(this.bulletlist1.draw(scene),
            scene.placeImageXY(new CircleImage(8, "solid", Color.PINK), 5, 5));
  }

  // test move for bullets
  boolean testMoveBullets(Tester t) {
    return t.checkExpect(this.mtBullet.move(), this.mtBullet)
        && t.checkExpect(this.bulletlist1.move(),
            new ConsLoBullet(new Bullet(8, 13, 5, Color.PINK, 8, 0, 2), this.mtBullet));
  }

  // test remove for bullets
  boolean testRemoveBullets(Tester t) {
    return t.checkExpect(this.mtBullet.remove(this.bullet1), this.mtBullet)
        && t.checkExpect(this.bulletlist1.remove(this.bullet1), this.mtBullet)
        && t.checkExpect(this.bulletlist2.remove(this.bullet2), this.bulletlist1);
  }

  // test hit any bullets for bullets
  boolean testHitAnyBullets(Tester t) {
    return t.checkExpect(this.mtBullet.hitAnyBullets(ship1), false)
        && t.checkExpect(this.bulletlist1.hitAnyBullets(ship1), true)
        && t.checkExpect(this.bulletlist3.hitAnyBullets(ship1), false)
        && t.checkExpect(this.bulletlist2.hitAnyBullets(ship1), true);

  }

  // test collisions for bullets
  boolean testBulletCollision(Tester t) {
    return t.checkExpect(this.mtBullet.collision(shiplist1, mtBullet), mtBullet)
        && t.checkExpect(this.bulletlist1.collision(shiplist1, mtBullet), bulletlist1)
        && t.checkExpect(this.bulletlist3.collision(shiplist1, mtBullet), mtBullet);

  }

  // test remove all for bullets
  boolean testRemoveAllBullets(Tester t) {
    return t.checkExpect(this.mtBullet.removeAll(bulletlist1), bulletlist1)
        && t.checkExpect(this.bulletlist1.removeAll(mtBullet), mtBullet)
        && t.checkExpect(this.bulletlist1.removeAll(bulletlist2), bulletlist3);

  }

  // test off for bullets
  boolean testOffScreenBullets(Tester t) {
    return t.checkExpect(this.mtBullet.off(mtBullet), mtBullet)
        && t.checkExpect(this.bulletlist1.off(mtBullet), mtBullet)
        && t.checkExpect(this.bulletlist2.off(mtBullet), mtBullet)
        && t.checkExpect(this.bulletlist4.off(mtBullet), new ConsLoBullet(bullet3, mtBullet));

  }

  // test bullets off screen
  boolean testAllBulletsOffScreen(Tester t) {
    return t.checkExpect(this.mtBullet.bulletsOffScreen(), true)
        && t.checkExpect(this.bulletlist1.bulletsOffScreen(), false)
        && t.checkExpect(this.bulletlist3.bulletsOffScreen(), false)
        && t.checkExpect(this.bulletlist5.bulletsOffScreen(), true);

  }

  // test append lists
  boolean testAppendLists(Tester t) {
    return t.checkExpect(this.mtBullet.append(mtBullet), mtBullet)
        && t.checkExpect(this.bulletlist1.append(mtBullet), bulletlist1)
        && t.checkExpect(this.bulletlist3.append(bulletlist1), bulletlist2);

  }
}