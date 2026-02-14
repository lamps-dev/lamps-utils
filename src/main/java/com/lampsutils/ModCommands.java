package com.lampsutils;

import com.lampsutils.config.ConfigManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.http.HttpResponse;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        registerHttpCommand(dispatcher);
    }

    private static void registerHttpCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("http")
                        .then(Commands.literal("get")
                                .then(Commands.argument("url", StringArgumentType.string())
                                        .executes(context -> {
                                            String url = StringArgumentType.getString(context, "url");
                                                if (ConfigManager.CONFIG.showHttpWarning()) {
                                                    context.getSource().sendSystemMessage(Component.literal(
                                                            "WARNING! This command may and will be unstable! Be careful when using this command since malicious actors can easily request/get data to/from a malicious server that the malicious actor added to steal your data!"
                                                    ));
                                                };

                                                context.getSource().sendSuccess(
                                                        () -> Component.literal("Fetching: " + url),
                                                        false
                                                );

                                                OkHttpClient client = new OkHttpClient();
                                                Request request = new Request.Builder().url(url).build();

                                                try (Response response = client.newCall(request).execute()) {
                                                    if (response.body() == null) {
                                                        context.getSource().sendFailure(Component.literal("Empty response body from: " + url));
                                                        return 0;
                                                    }

                                                    String body = response.body().string();
                                                    //int maxLength = body.length()/2;
                                                    int limit = ConfigManager.CONFIG.maxTextLimit();
                                                    if (body.length() > limit)
                                                        body = body.substring(0, limit);
                                                    context.getSource().sendSystemMessage(
                                                            Component.literal("Response: " + body + "..." + " - For less text limiting, change the option in Mod Menu Configs")
                                                    );

                                                    return 1;
                                                } catch (IOException e) {
                                                    context.getSource().sendFailure(
                                                            Component.literal("Failed to fetch: " + url + " Reason: " + e)
                                                                    .withStyle(style -> style.withColor(0xFF0000))
                                                    );
                                                    return 0;
                                                }
                                            })
                                )
                        )
        );
    }
}