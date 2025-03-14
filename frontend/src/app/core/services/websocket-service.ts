import { Injectable, signal } from '@angular/core';
import { GameEvent } from '@core/models/game-event.model';
import { environment } from '@environments/environment';
import { Client, IMessage } from '@stomp/stompjs';

// Voir https://github.com/sockjs/sockjs-client/issues/439#issuecomment-2202230917
import SockJS from 'sockjs-client/dist/sockjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient?: Client;
  gameEvents = signal<GameEvent[]>([]);

  constructor() {
    this.connect();
  }

  private connect() {
    // Création socket WebSocket avec SockJS
    const socket = new SockJS(environment.websocket.url);

    // Création du client STOMP
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000 // Auto-reconnexion
    });

    // Connexion STOMP
    this.stompClient.onConnect = () => {
      //console.log('🟢 Connecté à WebSocket via STOMP');
      // Souscription aux messages Kafka
      this.stompClient?.subscribe('/topic/movement', (message: IMessage) => {
        //console.log('📩 Message WebSocket reçu dans Angular :', message.body);
        const event: GameEvent = JSON.parse(message.body);
        this.gameEvents.update((msgs) => [...msgs, event]);
      });
    };

    // Activation du client STOMP
    this.stompClient.activate();
  }

  sendMessage(destination: string, message: string) {
    if (this.stompClient?.connected) {
      this.stompClient.publish({ destination, body: message });
    } else {
      console.error('❌ WebSocket non connecté');
    }
  }

  disconnect() {
    this.stompClient?.deactivate();
    console.log('🔴 Déconnecté de WebSocket');
  }
}
