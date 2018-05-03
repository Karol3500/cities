package org.posila.cities.config;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

public class EmbeddedMongoStarter {

    private static final String TEST_MONGO_HOST = "localhost";

    private static MongodStarter starter;

    public static void startMongo() throws IOException {

        if (starter != null) {
            int port = Network.getFreeServerPort();

            Command command = Command.MongoD;
            IDownloadConfig downloadConfig = new DownloadConfigBuilder()
                    .defaultsForCommand(command)
//                    .proxyFactory(new HttpProxyFactory("x", 8080))
                    .build();
            IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
                    .defaults(command)
                    .artifactStore(new ExtractedArtifactStoreBuilder()
                            .defaults(command)
                            .download(downloadConfig))
                    .build();
            starter = MongodStarter.getInstance(runtimeConfig);

            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(TEST_MONGO_HOST, port, Network.localhostIsIPv6()))
                    .build();

            MongodExecutable mongodExecutable = null;
            try {
                mongodExecutable = starter.prepare(mongodConfig);
                mongodExecutable.start();

            } finally {
                if (mongodExecutable != null)
                    mongodExecutable.stop();
            }
        }
    }
}
