package br.uefs.larsid.iot.soft.mqtt;

import br.uefs.larsid.iot.soft.models.NodeType;
import br.uefs.larsid.iot.soft.utils.MQTTClient;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ListenerDevices implements IMqttMessageListener {

  /*-------------------------Constantes---------------------------------------*/
  private static final int QOS = 1;
  /*--------------------------------------------------------------------------*/

  private MQTTClient MQTTClient;
  private final NodeType node;
  private static final Logger logger = Logger.getLogger(
    ListenerDevices.class.getName()
  );

  /**
   * Método construtor.
   *
   * @param MQTTClient MQTTClient -  Cliente MQTT.
   * @param node NodeType - Nó que executa o ListenerDevice.
   */
  public ListenerDevices(MQTTClient MQTTClient, NodeType node) {
    this.MQTTClient = MQTTClient;
    this.node = node;
  }

  /**
   * Se inscreve no tópico no qual o dispositivo irá responder às publicações
   * do nó.
   
   * @param deviceId String - ID do dispositivo para inscrever no tópico.
   */
  public void subscribe(String deviceId) {
    String topic = String.format("dev/%s/RES", deviceId);
    logger.info("Subscribing to: " + topic);

    this.MQTTClient.subscribe(QOS, this, topic);
  }

  /**
   * Se desinscreve do tópico de um dispositivo.
   *
   * @param deviceId String - ID do dispositivo para desinscrever do tópico.
   */
  public void unsubscribe(String deviceId) {
    String topic = String.format("dev/%s/RES", deviceId);
    logger.info("Unsubscribing from: " + topic);

    this.MQTTClient.unsubscribe(topic);
  }

  @Override
  public void messageArrived(String topic, MqttMessage message)
    throws Exception {
    final String mqttMessage = new String(message.getPayload());

    logger.info(mqttMessage);

    if (this.node.getWaitDeviceResponseTask() != null) {
      this.node.getWaitDeviceResponseTask().cancel();
    }
    // Avaliação de serviço prestado corretamente.
    this.node.getNode().evaluateDevice(1);
  }
}
