package br.com.dlbca.dynamicforms.test.infra;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;

/**
 * This class is used to control a Embed MongoDB
 * Instance for testing porposes.
 * 
 * @author Mateus
 *
 */
public class MongoDBSupport {
	
	private static final String LOCALHOST = "127.0.0.1";
	private static final String DB_NAME = "form-test";
	private static final int MONGO_TEST_PORT = 27028;
	private static MongodProcess mongoProcess;
	private static MongodExecutable mongoExecutable;
	private static Mongo mongo;
	
	@BeforeClass
    public static void initializeDB() throws IOException {
		MongodStarter runtime = MongodStarter.getDefaultInstance();

        mongoExecutable = runtime.prepare(new MongodConfig(Version.Main.PRODUCTION, MONGO_TEST_PORT, false));
        mongoProcess = mongoExecutable.start();

        mongo = new Mongo(LOCALHOST, MONGO_TEST_PORT);
        mongo.getDB(DB_NAME);
    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
        mongo.close();
        Thread.sleep(1000); // Used for avoiding socket exception during mongoProcess.stop() method
        mongoProcess.stop();
    }
    
}
