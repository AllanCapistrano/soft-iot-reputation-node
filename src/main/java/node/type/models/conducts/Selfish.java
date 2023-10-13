package node.type.models.conducts;

import java.util.logging.Logger;
import node.type.enums.ConductType;
import node.type.models.tangle.LedgerConnector;

public class Selfish extends Conduct {

  private static final Logger logger = Logger.getLogger(
    Selfish.class.getName()
  );

  /**
   * Método construtor.
   *
   * @param ledgerConnector LedgerConnector - Conector para comunicação com a Tangle.
   * @param id String - Identificador único do nó.
   */
  public Selfish(LedgerConnector ledgerConnector, String id) {
    super(ledgerConnector, id);
    this.defineConduct();
  }

  /**
   * Define o comportamento do nó.
   */
  @Override
  public void defineConduct() {
    this.setConductType(ConductType.SELFISH);
  }

  /**
   * Avalia o serviço que foi prestado pelo dispositivo, de acordo com o tipo de
   * comportamento do nó.
   *
   * @param deviceId String - Id do dispositivo que será avaliado.
   * @param value int - Valor da avaliação.
   * @throws InterruptedException
   */
  @Override
  public void evaluateDevice(String deviceId, int value)
    throws InterruptedException {
    logger.info("Selfish node does not evaluate the device.");
  }
}