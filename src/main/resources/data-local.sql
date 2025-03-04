-- Townsfolk
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Washerwoman', 'Townsfolk', 'You start knowing that 1 of 2 playrs is a particular Townsfolk.', 'https://wiki.bloodontheclocktower.com/Washerwoman'),
('Librarian', 'Townsfolk', 'You start knowing that 1 of 2 playrs is a particular Outsider, or that zero are in play.', 'https://wiki.bloodontheclocktower.com/Librarian'),
('Investigator', 'Townsfolk', 'You start knowing that 1 of 2 playrs is playrs particular Minion.', 'https://wiki.bloodontheclocktower.com/Investigator'),
('Chef', 'Townsfolk', 'You start knowing how many pairs of evil players there are.', 'https://wiki.bloodontheclocktower.com/Chef'),
('Empath', 'Townsfolk', 'Each night, you learn how many of your 2 alive neighbors are evil.', 'https://wiki.bloodontheclocktower.com/Empath'),
('Fortune Teller', 'Townsfolk', 'Each night, choose 2 playrs: you learn if either is a Demon. There is a good player that always registers as a Demon to you.', 'https://wiki.bloodontheclocktower.com/Fortune_Teller'),
('Undertaker', 'Townsfolk', 'Each night*, you learn which character died by execution today.', 'https://wiki.bloodontheclocktower.com/Undertaker'),
('Monk', 'Townsfolk', 'Each night*, choose a player (not yourself): they are safe from the Demon tonight.', 'https://wiki.bloodontheclocktower.com/Monk'),
('Ravenkeeper', 'Townsfolk', 'If you die at night, you are woken to choose a player: you learn their character.', 'https://wiki.bloodontheclocktower.com/Ravenkeeper'),
('Virgin', 'Townsfolk', 'The first time you are nominated, if the nominator is a Townsfolk, they are executed immediately.', 'https://wiki.bloodontheclocktower.com/Virgin'),
('Slayer', 'Townsfolk', 'Once per game, during the day, publicly choose a player: if they are the Demon, they die.', 'https://wiki.bloodontheclocktower.com/Slayer'),
('Soldier', 'Townsfolk', 'You are safe from the Demon.', 'https://wiki.bloodontheclocktower.com/Soldier'),
('Mayor', 'Townsfolk', 'If only 3 playrs live & no execution occurs, your team wins. If you die at night, another player might die instead.', 'https://wiki.bloodontheclocktower.com/Mayor');

-- Outsiders
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Butler', 'Outsider', 'Each night, choose a player (not yourself): tomorrow, you may only vote if they are voting too.', 'https://wiki.bloodontheclocktower.com/Butler'),
('Drunk', 'Outsider', 'You do not know you are the Drunk. You think you are a Townsfolk character, but you are not.', 'https://wiki.bloodontheclocktower.com/Drunk'),
('Recluse', 'Outsider', 'You might register as evil & as a Minion or Demon, even if dead.', 'https://wiki.bloodontheclocktower.com/Recluse'),
('Saint', 'Outsider', 'If you die by execution, your team loses.', 'https://wiki.bloodontheclocktower.com/Saint');

-- Minions
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Poisoner', 'Minion', 'Each night, choose a player: they are poisoned tonight and tomorrow day.', 'https://wiki.bloodontheclocktower.com/Poisoner'),
('Spy', 'Minion', 'Each night, you see the Grimoire. You might register as good & as a Townsfolk or Outsider, even if dead.', 'https://wiki.bloodontheclocktower.com/Spy'),
('Baron', 'Minion', 'There are 2 extra Outsiders in play.', 'https://wiki.bloodontheclocktower.com/Baron'),
('Scarlet Woman', 'Minion', 'If there are 5 or more players alive & the Demon dies, you become the Demon. (Travellers don''t count)', 'https://wiki.bloodontheclocktower.com/Scarlet_Woman');

-- Demon
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Imp', 'Demon', 'Each night*, choose a player: they die. If you kill yourself this way, a Minion becomes the Imp.', 'https://wiki.bloodontheclocktower.com/Imp');

----------------------------------------------------------------------------------------- Bad Moon Rising
-- Townsfolk
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Grandmother', 'Townsfolk', 'You start knowing a good player & their character. If the Demon kills them, you die too.', 'https://wiki.bloodontheclocktower.com/Grandmother'),
('Sailor', 'Townsfolk', 'Each night, choose an alive player: either you or they are drunk until dusk. You can''t die.', 'https://wiki.bloodontheclocktower.com/Sailor'),
('Chambermaid', 'Townsfolk', 'Each night, choose 2 alive players (not yourself): you learn how many woke tonight  due to their ability.', 'https://wiki.bloodontheclocktower.com/Chambermaid'),
('Exorcist', 'Townsfolk', 'Each night*, choose a player (different to last night): the Demon, if chosen, learns who you are then doesn''t wake tonight', 'https://wiki.bloodontheclocktower.com/Exorcist'),
('Innkeeper', 'Townsfolk', 'Each night*, choose 2 players: they can''t die tonight, but 1 is drunk until dusk.', 'https://wiki.bloodontheclocktower.com/Innkeeper'),
('Gambler', 'Townsfolk', 'Each night*, choose a player & guess their character: if you guess wrong, you die.', 'https://wiki.bloodontheclocktower.com/Gambler'),
('Gossip', 'Townsfolk', 'Each day, you may make a public statement. Tonight, if it was true, a player dies.', 'https://wiki.bloodontheclocktower.com/Gossip'),
('Courtier', 'Townsfolk', 'Once per game, at night, choose a character: they are drunk for 3 nights & 3 days.', 'https://wiki.bloodontheclocktower.com/Courtier'),
('Professor', 'Townsfolk', 'Once per game, at night*, choose a dead player: if they are a Townsfolk, they are resurrected.', 'https://wiki.bloodontheclocktower.com/Professor'),
('Minstrel', 'Townsfolk', 'When a Minion dies by execution, all other players (except Travellers) are drunk until dusk tomorrow.', 'https://wiki.bloodontheclocktower.com/Minstrel'),
('Tea Lady', 'Townsfolk', 'If both your alive neighbors are good, they can''t die.', 'https://wiki.bloodontheclocktower.com/Tea_Lady'),
('Pacifist', 'Townsfolk', 'Executed good players might not die.', 'https://wiki.bloodontheclocktower.com/Pacifist'),
('Fool', 'Townsfolk', 'The 1st time you die, you don''t.', 'https://wiki.bloodontheclocktower.com/Fool');

-- Outsiders
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Goon', 'Outsider', 'Each night, the 1st player to choose you with their ability is drunk until dusk. You become their alignment.', 'https://wiki.bloodontheclocktower.com/Goon'),
('Lunatic', 'Outsider', 'You think you are a Demon, but you are not. The Demon knows who you are & who you choose at night.', 'https://wiki.bloodontheclocktower.com/Lunatic'),
('Tinker', 'Outsider', 'You might die at any time.', 'https://wiki.bloodontheclocktower.com/Tinker'),
('Moonchild', 'Outsider', 'When you learn that you died, publicly choose 1 alive player. Tonight, if it was a good player, they die.', 'https://wiki.bloodontheclocktower.com/Moonchild');

-- Minions
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Godfather', 'Minion', 'You start knowing which Outsiders are in play. If 1 died today, choose a player tonight: they die. [-1 or +1 Outsider]', 'https://wiki.bloodontheclocktower.com/Godfather'),
('Assassin', 'Minion', 'Once per game, at night*, choose a player: they die, even if for some reason they could not.', 'https://wiki.bloodontheclocktower.com/Assassin'),
('Devil''s advocate', 'Minion', 'Each night, choose a living player (different to last night): if executed tomorrow, they don''t die.', 'https://wiki.bloodontheclocktower.com/Devil%27s_Advocate'),
('Mastermind', 'Minion', 'If the Demon dies by execution (ending the game), play for 1 more day. If a player is then executed, their team loses.', 'https://wiki.bloodontheclocktower.com/Mastermind');

-- Demons
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Zombuul', 'Demon', 'Each night*, if no-one died today, choose a player: they die. The 1st time you die, you live but register as dead.', 'https://wiki.bloodontheclocktower.com/Zombuul'),
('Pukka', 'Demon', 'Each night, choose a player: they are poisoned. The previously poisoned player dies then becomes healthy.', 'https://wiki.bloodontheclocktower.com/Pukka'),
('Shabaloth', 'Demon', 'Each night*, choose 2 players: they die. A dead player you chose last night might be regurgitated.', 'https://wiki.bloodontheclocktower.com/Shabaloth'),
('Po', 'Demon', 'Each night*, you may choose a player: they die. If your last choice was no-one, choose 3 players tonight.', 'https://wiki.bloodontheclocktower.com/Po');

---------------------------------------------------------------------------------- Sects & Violets

-- Townsfolk
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Clockmaker', 'Townsfolk', 'You start knowing how many steps from the Demon to its nearest Minion.', 'https://wiki.bloodontheclocktower.com/Clockmaker'),
('Dreamer', 'Townsfolk', 'Each night, choose a player (not yourself or Travellers): you learn 1 good & 1 evil character, 1 of which is correct.', 'https://wiki.bloodontheclocktower.com/Dreamer'),
('Snake Charmer', 'Townsfolk', 'Each night, choose an alive player: a chosen Demon swaps characters & alignments with you & is then poisoned.', 'https://wiki.bloodontheclocktower.com/Snake_Charmer'),
('Mathematician', 'Townsfolk', 'Each night, you learn how many players'' abilities worked abnormally (since dawn) due to another character''s ability.', 'https://wiki.bloodontheclocktower.com/Mathematician'),
('Flowergirl', 'Townsfolk', 'Each night*, you learn if a Demon voted today.', 'https://wiki.bloodontheclocktower.com/Flowergirl'),
('Town Crier', 'Townsfolk', 'Each night*, you learn if a Minion nominated today.', 'https://wiki.bloodontheclocktower.com/Town_Crier'),
('Oracle', 'Townsfolk', 'Each night*, you learn how many dead players are evil.', 'https://wiki.bloodontheclocktower.com/Oracle'),
('Savant', 'Townsfolk', 'Each day, you may visit the Storyteller to learn 2 things in private: 1 is true & 1 is false.', 'https://wiki.bloodontheclocktower.com/Savant'),
('Seamstress', 'Townsfolk', 'Once per game, at night, choose 2 players (not yourself): you learn if they are the same alignment.', 'https://wiki.bloodontheclocktower.com/Seamstress'),
('Philosopher', 'Townsfolk', 'Once per game, at night, choose a good character: gain that ability. If this character is in play, they are drunk.', 'https://wiki.bloodontheclocktower.com/Philosopher'),
('Artist', 'Townsfolk', 'Once per game, during the day, privately ask the Storyteller any yes/no question.', 'https://wiki.bloodontheclocktower.com/Artist'),
('Juggler', 'Townsfolk', 'On your 1st day, publicly guess up to 5 players'' characters. That night, you learn how many you got correct.', 'https://wiki.bloodontheclocktower.com/Juggler'),
('Sage', 'Townsfolk', 'If the Demon kills you, you learn that it is 1 of 2 players.', 'https://wiki.bloodontheclocktower.com/Sage');

-- Outsiders
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Mutant', 'Outsider', 'If you are "mad" about being an Outsider, you might be executed.', 'https://wiki.bloodontheclocktower.com/Mutant'),
('Sweetheart', 'Outsider', 'When you die, 1 player is drunk from now on.', 'https://wiki.bloodontheclocktower.com/Sweetheart'),
('Barber', 'Outsider', 'If you died today or tonight, the Demon may choose 2 players (not another Demon) to swap characters.', 'https://wiki.bloodontheclocktower.com/Barber'),
('Klutz', 'Outsider', 'When you learn that you died, publicly choose 1 alive player: if they are evil, your team loses.', 'https://wiki.bloodontheclocktower.com/Klutz');

-- Minions
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Evil Twin', 'Minion', 'You & an opposing player know each other. If the good player is executed, evil wins. Good can''t win if you both live.', 'https://wiki.bloodontheclocktower.com/Evil_Twin'),
('Witch', 'Minion', 'Each night, choose a player: if they nominate tomorrow, they die. If just 3 players live, you lose this ability.', 'https://wiki.bloodontheclocktower.com/Witch'),
('Cerenovus', 'Minion', 'Each night, choose a player & a good character: they are "mad" they are this character tomorrow, or might be executed.', 'https://wiki.bloodontheclocktower.com/Cerenovus'),
('Pit-Hag', 'Minion', 'Each night*, choose a player & a character they become (if not in play). If a Demon is made, deaths tonight are arbitrary.', 'https://wiki.bloodontheclocktower.com/Pit-Hag');

-- Demons
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Fang Gu', 'Demon', 'Each night*, choose a player: they die. The 1st Outsider this kills becomes an evil Fang Gu & you die instead. [+1 Outsider]', 'https://wiki.bloodontheclocktower.com/Fang_Gu'),
('Vigormortis', 'Demon', 'Each night*, choose a player: they die. Minions you kill keep their ability & poison 1 Townsfolk neighbor. [-1 Outsider]', 'https://wiki.bloodontheclocktower.com/Vigormortis'),
('No Dashii', 'Demon', 'Each night*, choose a player: they die. Your 2 Townsfolk neighbors are poisoned.', 'https://wiki.bloodontheclocktower.com/No_Dashii'),
('Vortox', 'Demon', 'Each night*, choose a player: they die. Townsfolk abilities yield false info. Each day, if no-one is executed, evil wins.', 'https://wiki.bloodontheclocktower.com/Vortox');

-- Experimental

-- Townsfolk
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Acrobat', 'Townsfolk', 'Each night*, choose a player: if they are or become drunk or poisoned tonight, you die.', 'https://wiki.bloodontheclocktower.com/Acrobat'),
('Alchemist', 'Townsfolk', 'You have a Minion ability. When using this, the Storyteller may prompt you to choose differently.', 'https://wiki.bloodontheclocktower.com/Alchemist'),
('Alsaahir', 'Townsfolk', 'Each day, if you publicly guess which players are Minion(s) and which are Demon(s), good wins.', 'https://wiki.bloodontheclocktower.com/Alsaahir'),
('Amnesiac', 'Townsfolk', 'You do not know what your ability is. Each day, privately guess what it is: you learn how accurate you are.', 'https://wiki.bloodontheclocktower.com/Amnesiac'),
('Atheist', 'Townsfolk', 'The Storyteller can break the game rules, and if executed, good wins, even if you are dead. [No evil characters]', 'https://wiki.bloodontheclocktower.com/Atheist'),
('Balloonist', 'Townsfolk', 'Each night, you learn a player of a different character type than last night. [+0 or +1 Outsider]', 'https://wiki.bloodontheclocktower.com/Balloonist'),
('Banshee', 'Townsfolk', 'If the Demon kills you, all players learn this. From now on, you may nominate twice per day and vote twice per nomination.', 'https://wiki.bloodontheclocktower.com/Banshee'),
('Bounty Hunter', 'Townsfolk', 'You start knowing 1 evil player. If the player you know dies, you learn another evil player tonight. [1 Townsfolk is evil]', 'https://wiki.bloodontheclocktower.com/Bounty_Hunter'),
('Cannibal', 'Townsfolk', 'You have the ability of the recently killed executee. If they are evil, you are poisoned until a good player dies by execution.', 'https://wiki.bloodontheclocktower.com/Cannibal'),
('Choirboy', 'Townsfolk', 'If the Demon kills the King, you learn which player is the Demon. [+the King]', 'https://wiki.bloodontheclocktower.com/Choirboy'),
('Cult Leader', 'Townsfolk', 'Each night, you become the alignment of an alive neighbor. If all good players choose to join your cult, your team wins.', 'https://wiki.bloodontheclocktower.com/Cult_Leader'),
('Engineer', 'Townsfolk', 'Once per game, at night, choose which Minions or which Demon is in play.', 'https://wiki.bloodontheclocktower.com/Engineer'),
('Farmer', 'Townsfolk', 'When you die at night, an alive good player becomes a Farmer.', 'https://wiki.bloodontheclocktower.com/Farmer'),
('Fisherman', 'Townsfolk', 'Once per game, during the day, visit the Storyteller for some advice to help your team win.', 'https://wiki.bloodontheclocktower.com/Fisherman'),
('General', 'Townsfolk', 'Each night, you learn which alignment the Storyteller believes is winning: good, evil, or neither.', 'https://wiki.bloodontheclocktower.com/General'),
('High Priestess', 'Townsfolk', 'Each night, learn which player the Storyteller believes you should talk to most.', 'https://wiki.bloodontheclocktower.com/High_Priestess'),
('Huntsman', 'Townsfolk', 'Once per game, at night, choose a living player: the Damsel, if chosen, becomes a not-in-play Townsfolk. [+the Damsel]', 'https://wiki.bloodontheclocktower.com/Huntsman'),
('King', 'Townsfolk', 'Each night, if the dead equal or outnumber the living, you learn 1 alive character. The Demon knows you are the King.', 'https://wiki.bloodontheclocktower.com/King'),
('Knight', 'Townsfolk', 'You start knowing 2 players that are not the Demon.', 'https://wiki.bloodontheclocktower.com/Knight'),
('Lycanthrope', 'Townsfolk', 'Each night*, choose an alive player. If good, they die & the Demon doesn’t kill tonight. One good player registers as evil.', 'https://wiki.bloodontheclocktower.com/Lycanthrope'),
('Magician', 'Townsfolk', 'The Demon thinks you are a Minion. Minions think you are a Demon.', 'https://wiki.bloodontheclocktower.com/Magician'),
('Nightwatchman', 'Townsfolk', 'Once per game, at night, choose a player: they learn you are the Nightwatchman.', 'https://wiki.bloodontheclocktower.com/Nightwatchman'),
('Noble', 'Townsfolk', 'You start knowing 3 players, 1 and only 1 of which is evil.', 'https://wiki.bloodontheclocktower.com/Noble'),
('Pixie', 'Townsfolk', 'You start knowing 1 in-play Townsfolk. If you were mad that you were this character, you gain their ability when they die.', 'https://wiki.bloodontheclocktower.com/Pixie'),
('Poppy Grower', 'Townsfolk', 'Minions & Demons do not know each other. If you die, they learn who each other are that night.', 'https://wiki.bloodontheclocktower.com/Poppy_Grower'),
('Preacher', 'Townsfolk', 'Each night, choose a player: a Minion, if chosen, learns this. All chosen Minions have no ability.', 'https://wiki.bloodontheclocktower.com/Preacher'),
('Shugenja', 'Townsfolk', 'You start knowing if your closest evil player is clockwise or anti-clockwise. If equidistant, this info is arbitrary.', 'https://wiki.bloodontheclocktower.com/Shugenja'),
('Steward', 'Townsfolk', 'You start knowing 1 good player.', 'https://wiki.bloodontheclocktower.com/Steward'),
('Village Idiot', 'Townsfolk', 'Each night, choose a player: you learn their alignment. [+0 to +2 Village Idiots. 1 of the extras is drunk]', 'https://wiki.bloodontheclocktower.com/Village_Idiot');

-- Outsiders
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Damsel', 'Outsider', 'All Minions know a Damsel is in play. If a Minion publicly guesses you (once), your team loses.', 'https://wiki.bloodontheclocktower.com/Damsel'),
('Golem', 'Outsider', 'You may only nominate once per game. When you do, if the nominee is not the Demon, they die.', 'https://wiki.bloodontheclocktower.com/Golem'),
('Hatter', 'Outsider', 'If you died today or tonight, the Minion & Demon players may choose new Minion & Demon characters to be.', 'https://wiki.bloodontheclocktower.com/Hatter'),
('Heretic', 'Outsider', 'Whoever wins, loses & whoever loses, wins, even if you are dead.', 'https://wiki.bloodontheclocktower.com/Heretic'),
('Ogre', 'Outsider', 'On your 1st night, choose a player (not yourself): you become their alignment (you don''t know which) even if drunk or poisoned.', 'https://wiki.bloodontheclocktower.com/Ogre'),
('Plague Doctor', 'Outsider', 'When you die, the Storyteller gains a Minion ability.', 'https://wiki.bloodontheclocktower.com/Plague_Doctor'),
('Politician', 'Outsider', 'If you were the player most responsible for your team losing, you change alignment & win, even if dead.', 'https://wiki.bloodontheclocktower.com/Politician'),
('Puzzlemaster', 'Outsider', '1 player is drunk, even if you die. If you guess (once) who it is, learn the Demon player, but guess wrong & get false info.', 'https://wiki.bloodontheclocktower.com/Puzzlemaster'),
('Snitch', 'Outsider', 'Each Minion gets 3 bluffs.', 'https://wiki.bloodontheclocktower.com/Snitch'),
('Zealot', 'Outsider', 'If there are 5 or more players alive, you must vote for every nomination.', 'https://wiki.bloodontheclocktower.com/Zealot');

-- Minions
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Boffin', 'Minion', 'The Demon (even if drunk or poisoned) has a not-in-play good character''s ability. You both know which.', 'https://wiki.bloodontheclocktower.com/Boffin'),
('Boomdandy', 'Minion', 'If you are executed, all but 3 players die. After a 10 to 1 countdown, the player with the most players pointing at them, dies.', 'https://wiki.bloodontheclocktower.com/Boomdandy'),
('Fearmonger', 'Minion', 'Each night, choose a player: if you nominate & execute them, their team loses. All players know if you choose a new player.', 'https://wiki.bloodontheclocktower.com/Fearmonger'),
('Goblin', 'Minion', 'If you publicly claim to be the Goblin when nominated & are executed that day, your team wins.', 'https://wiki.bloodontheclocktower.com/Goblin'),
('Harpy', 'Minion', 'Each night, choose 2 players: tomorrow, the 1st player is mad that the 2nd is evil, or one or both might die.', 'https://wiki.bloodontheclocktower.com/Harpy'),
('Marionette', 'Minion', 'You think you are a good character, but you are not. The Demon knows who you are. [You neighbor the Demon]', 'https://wiki.bloodontheclocktower.com/Marionette'),
('Mezepheles', 'Minion', 'You start knowing a secret word. The 1st good player to say this word becomes evil that night.', 'https://wiki.bloodontheclocktower.com/Mezepheles'),
('Organ Grinder', 'Minion', 'All players keep their eyes closed when voting and the vote tally is secret. Each night, choose if you are drunk until dusk.', 'https://wiki.bloodontheclocktower.com/Organ_Grinder'),
('Psychopath', 'Minion', 'Each day, before nominations, you may publicly choose a player: they die. If executed, you only die if you lose roshambo.', 'https://wiki.bloodontheclocktower.com/Psychopath'),
('Summoner', 'Minion', 'You get 3 bluffs. On the 3rd night, choose a player: they become an evil Demon of your choice. [No Demon]', 'https://wiki.bloodontheclocktower.com/Summoner'),
('Vizier', 'Minion', 'All players know you are the Vizier. You cannot die during the day. If good voted, you may choose to execute immediately.', 'https://wiki.bloodontheclocktower.com/Vizier'),
('Widow', 'Minion', 'On your first night, look at the Grimoire & choose a player: they are poisoned. 1 good player knows a Widow is in play.', 'https://wiki.bloodontheclocktower.com/Widow'),
('Wizard', 'Minion', 'Once per game, choose to make a wish. If granted, it might have a price & leave a clue as to its nature.', 'https://wiki.bloodontheclocktower.com/Wizard'),
('Xaan', 'Minion', 'On night X, all Townsfolk are poisoned until dusk. [X Outsiders]', 'https://wiki.bloodontheclocktower.com/Xaan');

-- Demons
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Al-Hadikhia', 'Demon', 'Each night*, you may choose 3 players (all players learn who): each silently chooses to live or die, but if all live, all die.', 'https://wiki.bloodontheclocktower.com/Al-Hadikhia'),
('Kazali', 'Demon', 'Each night*, choose a player: they die. [You choose which players are which Minions. -? to +? Outsiders]', 'https://wiki.bloodontheclocktower.com/Kazali'),
('Legion', 'Demon', 'Each night*, a player might die. Executions fail if only evil voted. You register as a Minion too. [Most players are Legion]', 'https://wiki.bloodontheclocktower.com/Legion'),
('Leviathan', 'Demon', 'If more than 1 good player is executed, evil wins. All players know you are in play. After day 5, evil wins.', 'https://wiki.bloodontheclocktower.com/Leviathan'),
('Lil Monsta', 'Demon', 'Each night, Minions choose who babysits Lil'' Monsta & ''is the Demon''. Each night*, a player might die. [+1 Minion]', 'https://wiki.bloodontheclocktower.com/Lil%27_Monsta'),
('Lleech', 'Demon', 'Each night*, choose a player: they die. You start by choosing a player: they are poisoned. You die if & only if they are dead.', 'https://wiki.bloodontheclocktower.com/Lleech'),
('Lord of Typhon', 'Demon', 'Each night*, choose a player: they die. [Evil characters are in a line. You are in the middle. +1 Minion. -? to +? Outsiders]', 'https://wiki.bloodontheclocktower.com/Lord_of_Typhon'),
('Ojo', 'Demon', 'Each night*, choose a character: they die. If they are not in play, the Storyteller chooses who dies.', 'https://wiki.bloodontheclocktower.com/Ojo'),
('Riot', 'Demon', 'On day 3, Minions become Riot & nominees die but nominate an alive player immediately. This must happen.', 'https://wiki.bloodontheclocktower.com/Riot'),
('Yaggababble', 'Demon', 'You start knowing a secret phrase. For each time you said it publicly today, a player might die.', 'https://wiki.bloodontheclocktower.com/Yaggababble');

-- Travellers
INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Scapegoat', 'Traveller', 'If a player of your alignment is executed, you might be executed instead.', 'https://wiki.bloodontheclocktower.com/Scapegoat'),
('Gunslinger', 'Traveller', 'Each day, after the 1st vote has been tallied, you may choose a player that voted: they die.', 'https://wiki.bloodontheclocktower.com/Gunslinger'),
('Beggar', 'Traveller', 'You must use a vote token to vote. If a dead player gives you theirs, you learn their alignment. You are sober and healthy.', 'https://wiki.bloodontheclocktower.com/Beggar'),
('Bureaucrat', 'Traveller', 'Each night, choose a player (not yourself): their vote counts as 3 votes tomorrow.', 'https://wiki.bloodontheclocktower.com/Bureaucrat'),
('Thief', 'Traveller', 'Each night, choose a player (not yourself): their vote counts negatively tomorrow.', 'https://wiki.bloodontheclocktower.com/Thief'),
('Butcher', 'Traveller', 'Each day, after the 1st execution, you may nominate again.', 'https://wiki.bloodontheclocktower.com/Butcher'),
('Bone Collector', 'Traveller', 'Once per game, at night*, choose a dead player: they regain their ability until dusk.', 'https://wiki.bloodontheclocktower.com/Bone_Collector'),
('Harlot', 'Traveller', 'Each night*, choose a living player: if they agree, you learn their character, but you both might die.', 'https://wiki.bloodontheclocktower.com/Harlot'),
('Barista', 'Traveller', 'Each night, until dusk, 1) a player becomes sober, healthy & gets true info, or 2) their ability works twice. They learn which.', 'https://wiki.bloodontheclocktower.com/Barista'),
('Deviant', 'Traveller', 'If you were funny today, you cannot die by exile.', 'https://wiki.bloodontheclocktower.com/Deviant'),
('Apprentice', 'Traveller', 'On your 1st night, you gain a Townsfolk ability (if good) or a Minion ability (if evil).', 'https://wiki.bloodontheclocktower.com/Apprentice'),
('Matron', 'Traveller', 'Each day, you may choose up to 3 sets of 2 players to swap seats. Players may not leave their seats to talk in private.', 'https://wiki.bloodontheclocktower.com/Matron'),
('Voudon', 'Traveller', 'Only you & the dead can vote. They don''t need a vote token to do so. A 50% majority isn''t required.', 'https://wiki.bloodontheclocktower.com/Voudon'),
('Judge', 'Traveller', 'Once per game, if another player nominated, you may choose to force the current execution to pass or fail.', 'https://wiki.bloodontheclocktower.com/Judge'),
('Bishop', 'Traveller', 'Only the Storyteller can nominate. At least 1 opposing player must be nominated each day.', 'https://wiki.bloodontheclocktower.com/Bishop'),
('Gangster', 'Traveller', 'Once per day, you may choose to kill an alive neighbor, if your other alive neighbor agrees.', 'https://wiki.bloodontheclocktower.com/Gangster'),
('Gnome', 'Traveller', 'All players start knowing a player of your alignment. You may choose to kill anyone who nominates them.', 'https://wiki.bloodontheclocktower.com/Gnome');

INSERT INTO character_ (name, alignment, description, link_to_wiki) VALUES
('Doomsayer', 'Fabled', 'If 4 or more players live, each living player may publicly choose (once per game) that a player of their own alignment dies.', 'https://wiki.bloodontheclocktower.com/Doomsayer'),
('Angel', 'Fabled', 'Something bad might happen to whoever is most responsible for the death of a new player.', 'https://wiki.bloodontheclocktower.com/Angel'),
('Buddhist', 'Fabled', 'For the first 2 minutes of each day, veteran players may not talk.', 'https://wiki.bloodontheclocktower.com/Buddhist'),
('Hell''s Librarian', 'Fabled', 'Something bad might happen to whoever talks when the Storyteller has asked for silence.', 'https://wiki.bloodontheclocktower.com/Hell''s_Librarian'),
('Revolutionary', 'Fabled', '2 neighboring players are known to be the same alignment. Once per game, 1 of them registers falsely.', 'https://wiki.bloodontheclocktower.com/Revolutionary'),
('Fiddler', 'Fabled', 'Once per game, the Demon secretly chooses an opposing player: all players choose which of these 2 players win.', 'https://wiki.bloodontheclocktower.com/Fiddler'),
('Toymaker', 'Fabled', 'The Demon may choose not to attack & must do this at least once per game. Evil players get normal starting info.', 'https://wiki.bloodontheclocktower.com/Toymaker'),
('Fibbin', 'Fabled', 'Once per game, 1 good player might get incorrect information.', 'https://wiki.bloodontheclocktower.com/Fibbin'),
('Duchess', 'Fabled', 'Each day, 3 players may choose to visit you. At night*, each visitor learns how many visitors are evil, but 1 gets false info.', 'https://wiki.bloodontheclocktower.com/Duchess'),
('Sentinel', 'Fabled', 'There might be 1 extra or 1 fewer Outsider in play.', 'https://wiki.bloodontheclocktower.com/Sentinel'),
('Spirit of Ivory', 'Fabled', 'There can''t be more than 1 extra evil player.', 'https://wiki.bloodontheclocktower.com/Spirit_of_Ivory'),
('Djinn', 'Fabled', 'Use the Djinn''s special rule. All players know what it is.', 'https://wiki.bloodontheclocktower.com/Djinn'),
('Bootlegger', 'Fabled', 'This script has homebrew characters or rules.', 'https://wiki.bloodontheclocktower.com/Bootlegger'),
('Ferryman', 'Fabled', 'On the final day, all dead players regain their vote token.', 'https://wiki.bloodontheclocktower.com/Ferryman'),
('Gardener', 'Fabled', 'The Storyteller assigns 1 or more players'' characters.', 'https://wiki.bloodontheclocktower.com/Gardener'),
('Storm Catcher', 'Fabled', 'Name a good character. If in play, they can only die by execution, but evil players learn which player it is.', 'https://wiki.bloodontheclocktower.com/Storm_Catcher');

INSERT INTO player (name) values
('Kamil'),
('Bartek'),
('Kamila'),
('Piter'),
('Przemek'),
('Basia'),
('Wojtek'),
('Robert'),
('Patrycja'),
('Łukasz'),
('Julia'),
('Michał'),
('Juliana'),
('Ali');

INSERT INTO script (name) values
('Trouble Brewing'),
('Bad Moon Rising'),
('Sects & Violets');

INSERT INTO script_characters (script_id, characters_id) values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 21),
(1, 22),
(2, 23),
(2, 24),
(2, 25),
(2, 26),
(2, 27),
(2, 28),
(2, 29),
(2, 30),
(2, 31),
(2, 32),
(2, 33),
(2, 34),
(2, 35),
(2, 36),
(2, 37),
(2, 38),
(2, 39),
(2, 40),
(2, 41),
(2, 42),
(2, 43),
(2, 44),
(2, 45),
(2, 46),
(2, 47),
(3, 48),
(3, 49),
(3, 50),
(3, 51),
(3, 52),
(3, 53),
(3, 54),
(3, 55),
(3, 56),
(3, 57),
(3, 58),
(3, 59),
(3, 60),
(3, 61),
(3, 62),
(3, 63),
(3, 64),
(3, 65),
(3, 66),
(3, 67),
(3, 68),
(3, 69),
(3, 70),
(3, 71),
(3, 72);