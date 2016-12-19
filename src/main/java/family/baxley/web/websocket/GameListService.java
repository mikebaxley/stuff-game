package family.baxley.web.websocket;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import family.baxley.stuff.Game;

@Controller
public class GameListService {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);
    
    @Autowired
    private List<Game> gameList;
	
	@MessageMapping("/sendgamelist")
    @SendTo("/topic/gamelistener")
    public List<Game> sendGameList() {
		log.debug("Heard a game list, here is my game list: " + gameList);
        return gameList;
    }
}
