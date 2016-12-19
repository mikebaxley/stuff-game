package family.baxley.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import family.baxley.stuff.Game;
import family.baxley.web.rest.vm.CreateGameVM;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping("/api")
public class GameResource {
	private static final Logger log = LoggerFactory.getLogger(GameResource.class);
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private List<Game> gameList;
	
    @GetMapping("/games")
    @Timed
    public List<Game> getList() {
    	log.debug("GameResource.gameList");
    	return gameList;
    }

    
    
    @PostMapping("/games")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Timed
    public void createGame(@RequestBody CreateGameVM jsonLogger) {
    	log.debug("GameResource.createGame [" +jsonLogger+"]");
    	
    	gameList.add(new Game(jsonLogger.getGameName()));
    	
    	log.debug("Sending game list");
    	this.template.convertAndSend("/sendgamelist", gameList);
    }
}
