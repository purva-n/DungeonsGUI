package dungeon.model;

public enum ShootStatus {
  OUT_OF_ARROWS(-1),
  DIDNT_HIT(0),
  INJURED(1),
  KILLED(2);

  private final int shoot;
  ShootStatus(int status) {
    shoot = status;
  }

  public int getShoot() {
    return shoot;
  }

}
