package com.lampsutils;

import com.lampsutils.config.ConfigManager;
import com.lampsutils.lib.HttpUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.context.CommandContext;

public class ModCommands {

    public class Colors {
        public static final int INFO = 0x55FFFF;
        public static final int SUCCESS = 0x55FF55;
        public static final int WARNING = 0xFFAA00;
        public static final int ERROR = 0xFF5555;
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        registerHttpCommand(dispatcher);
        registerCurlCommand(dispatcher);
    }

    private static void registerCurlCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("curl")
                        .then(Commands.argument("url", StringArgumentType.string())
                                .executes(context -> executeHttpGet(context, StringArgumentType.getString(context, "url")))
                        )
        );
    }

    private static int executeHttpGet(CommandContext<CommandSourceStack> context, String url) {
        if (!ConfigManager.CONFIG.httpEnabled()) {
            context.getSource().sendFailure(Component.literal("HTTP commands are disabled in the configuration."));
            return 0;
        }

        if (ConfigManager.CONFIG.showHttpWarning()) {
            context.getSource().sendSystemMessage(Component.literal(
                    "WARNING! This command may and will be unstable! Be careful when using this command since malicious actors can easily request/get data to/from a malicious server that the malicious actor added to steal your data!")
                    .withStyle(style -> style.withColor(Colors.WARNING))
            );
        }

        context.getSource().sendSuccess(
                () -> Component.literal("Fetching: " + url)
                        .withStyle(style -> style.withColor(Colors.INFO)),
                false
        );

        try {
            String body = HttpUtil.get(url);
            if (body == null || body.isEmpty()) {
                context.getSource().sendFailure(Component.literal("Empty response body from: " + url)
                        .withStyle(style -> style.withColor(Colors.ERROR))
                );
                return 0;
            }

            int limit = ConfigManager.CONFIG.maxTextLimit();
            boolean truncated = body.length() > limit;
            final String finalBody = truncated ? body.substring(0, limit) : body;
            String suffix = truncated ? "... (truncated, change limit in Mod Menu Configs)" : "";

            context.getSource().sendSystemMessage(
                    Component.literal("Response: " + finalBody + suffix)
            );

            return 1;
        } catch (Exception e) {
            context.getSource().sendFailure(
                    Component.literal("Failed to fetch: " + url + "| Reason: " + e.getMessage())
                            .withStyle(style -> style.withColor(Colors.ERROR).withBold(true))
            );
            return 0;
        }
    }

    private static void registerHttpCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("http")
                        .then(Commands.literal("get")
                                .then(Commands.argument("url", StringArgumentType.string())
                                        .executes(context -> executeHttpGet(context, StringArgumentType.getString(context, "url")))
                                )
                        )
        );
    }
}