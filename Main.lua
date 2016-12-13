function onBorn(bot)

end

function onNewTurn(turn)
	
end

function onBotUpdate(bot, turn)
	if (math.random() < 0.5) then
		bot:forward()
	else
		bot:rotateLeft()
	end
end

function onDeath(bot)
	
end