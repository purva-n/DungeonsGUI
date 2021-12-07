package dungeon.model;

/**
 * Abstract class for the {@link EntityInLocation} interface.
 */
abstract class AbstractEntity implements EntityInLocation {

  protected int quantity = 0;

  /**
   * Method to update the quantity of the entity in the {@link Location}.
   *
   * @param quantity integer value of the delta or difference to be updated.
   */
  @Override
  public void updateQuantity(int quantity) {
    if (this.quantity == 0 && quantity < 0) {
      return;
    }
    this.quantity += quantity;
  }

  /**
   * Gets the quantity of the entity in the Location.
   *
   * @return integer value of quantity of the reference item in the Location.
   */
  public int getQuantity() {
    return quantity;
  }
}
