package com.groupSeventeen;

import com.groupSeventeen.Util.Gate;

import java.util.ArrayList;
import java.util.List;

public class QuestionCreator {
    public static void createQuestions() {
        List<Question> questions = new ArrayList<>();
        // Question(int id, String category, String text,  String[] answers, int correctAnswer)
        // geography
        questions.add(new Question(1, "geography", "What is the largest country in the world?", new String[]{"China","Greenland","USA","Russia" ,"India","Australia"}, 3));
        questions.add(new Question(2, "geography", "What is the smallest country in the world?", new String[]{"Monaco","San Marino","Vatican City" ,"Malta","Barbados","Luxembourg"},2));
        questions.add(new Question(3,"geography","What is the hottest continent on Earth?" , new String[]{"Asia","Europe","America","Antarctica","Australia","Africa"}, 5));
        questions.add(new Question(4,"geography","What is the longest river in the world?", new String[]{"Amazon","River Nile","Mississippi","Niger","Yukon","Zambezi"},1));
        questions.add(new Question(5,"geography","The tallest building in the world is located in which city?", new String[]{"Dubai","Shanghai","Mecca","Seoul","New York City","Beijing"},0));
        questions.add(new Question(6,"geography","What is the largest landlocked country in the world by size?", new String[]{"Niger","Mali","Bolivia","Kazakhstan","Afghanistan","Zambia"},3));
        questions.add(new Question(7,"geography", "How many countries are there in the region of Europe? (Recognised by the United Nations)", new String[]{"23","40","42","43","44","45"},4));
        questions.add(new Question(8,"geography","How many permanent members are there on the UN security council?", new String[]{"5","3","13","4","9","7"},0));
        questions.add(new Question(9,"geography","What is the tallest mountain in the world?", new String[]{"Tirich Mir","Diran","Mount Everest","Zugspitze","Makulu","Kangchenjunga"},2));
        questions.add( new Question(10,"geography","What is the capital of Barbados?", new String[]{"Holetown","Oistins","Speightstown","Bridgetown","no capital","capital city"},3));
        questions.add( new Question(11,"geography", "Bohemian Switzerland National Park is in which country?", new String[]{"Switzerland","Austria","Germany","Slovakia","Czech Republic","Poland"},4));
        questions.add( new Question(12,"geography","Which lake is often described as the jewel of the Italian lakes?", new String[]{"Lake Garda","Lake Maggiore","Lake Como","Lake Orta","Lake Iseo","Lake Bracciano"},2));
        questions.add( new Question(13,"geography","In which country would you find the original Legoland?", new String[]{"Norway","Sweden","Germany","Denmark","Switzerland","Belgium"},3));
        questions.add( new Question(14,"geography","How many countries are there in the world?", new String[]{"196","201","188","192","173","180"},0));
        questions.add( new Question(15,"geography","What body of water separates Africa and Europe?", new String[]{"BaffinBay","The Strait of Gibraltar","Atlantic Ocean","Arctic Ocean","Pacific Ocean","Medditerranean Sea"},1));
        questions.add( new Question(16,"geography","What is the only major city located on two continents?", new String[]{"Moscow","Saint Petersburg","Istanbul","Bucharest","Minsk","Warsaw"},2));
        questions.add( new Question(17,"geography","What is the oldest active volcano on Earth?", new String[]{"Mount Yasur","Stromboli","Mount Etna","Mount Erebos","Mount Merapi","Erta Ale"},2));
        questions.add( new Question(18,"geography","Where is the northernmost point of land in the world?", new String[]{"Russia", "Sweden","Norway","Greenland","Finnland","Svalbard"},3));
        questions.add( new Question(19,"geography","The deepest trench in the world, the Mariana Trench, is located in which ocean?", new String[]{"Arctic Ocean","Atlantic Ocean","Indian Ocean","Antarctic Ocean","Pacific Ocean","World Ocean"},4));
        questions.add( new Question(20,"geography","Which is the fastest flowing river in the world?", new String[]{"River Nile","Mississipi","Niger","Donau","Zambezi","Amazon"},5));


        // sports
        questions.add(new Question(21, "sports", "Which colour is not in the five Olypic rings?",new String[]{"orange","blue","yellow","black","green","red"},0));
        questions.add( new Question(22, "sports","Who won the FIFA Women's World Cup in 2019?",new String[]{"Germany","USA","Italy","Netherlands","Sweden","England"},1));
        questions.add(new Question(23, "sports","In bowling, what is the term given for three consecutive strikes?",new String[]{"Strike Three","Spare","A turkey","Tripple","Bagger","Wild Strike"},2));
        questions.add(new Question(24, "sports","Which footballer has the most Instagram followers in the world - as of 2020?",new String[]{"Lionel Messi","Neymar","Kylian Mbappe","Christiano Ronaldo","Paul Pogba","Diego Maradona"},3));
        questions.add(new Question(25, "sports","Which city will host the 2028 Olympic Games?",new String[]{"Sydney","Berlin","Tokyo","Moscow","Los Angeles","Rio de Janero"},4));
        questions.add(new Question(26, "sports","How many horses are on each team in a polo match?",new String[]{"7","2","5","11","8","4"},5));
        questions.add(new Question(27, "sports","Where is the US Masters golf tournament held?",new String[]{"Augusta National Golf Club","Kiawah Island Golf Resort","Tiburon Golf Club","Pebble Beach Golf Links","Old South Golf Links","The Boca Country Club"},0));
        questions.add(new Question(28, "sports","How many world titles has Phil Talyor won in darts?",new String[]{"12","16","7","21","15","19"},1));
        questions.add(new Question(29, "sports","When was the last time an American playerwon the gold medal in the Olympic Games Men’s Singles tournament?",new String[]{"2004","1992","1996","2010","2018","2008"},2));
        questions.add(new Question(30, "sports","In which round did Tyson Fury defeat Deontay Wilder in their second showdown?",new String[]{"3rd","10th","4th","7th","1st","8th"},3));
        questions.add(new Question(31, "sports","Who did John Cena debut against?",new String[]{"The Rock","The Undertaker","Randy Orton","Drew McIntyre","Kurt Angle","Braun Strowman"},4));
	/*Question s12 = new Question(32, "sports",,new String[]{,,,,,},);
	Question s13 = new Question(33, "sports",,new String[]{,,,,,},);
	Question s14 = new Question(34, "sports",,new String[]{,,,,,},);
	Question s15 = new Question(35, "sports",,new String[]{,,,,,},);
	Question s16 = new Question(36, "sports",,new String[]{,,,,,},);
	Question s17 = new Question(37, "sports",,new String[]{,,,,,},);
	Question s18 = new Question(38, "sports",,new String[]{,,,,,},);
	Question s19 = new Question(39, "sports",,new String[]{,,,,,},);
	Question s20 = new Question(40, "sports",,new String[]{,,,,,},);*/



        // history
        questions.add(new Question(41, "history","Which nationality was the polar explorer Roald Amundsen?",new String[]{"Norwegian","Swede","German","American","Dane","Russian"},0));
        questions.add(new Question(42, "history","Who was the first female Prime Minister of Australia (2010-2013)?",new String[]{"Carol Brown","Julia Gillard","Paulin Hansen","Jennifer McAllister","Louise Pratt","Larissa Waters"},1));
        questions.add(new Question(43, "history","The first successful vaccine was introduced by Edward Jenner in 1796. Which disease did it guard against?",new String[]{"Cholera","Tetanus","Smallpox","Typhoid fever","Tuberculosis","Diphtheria"},2));
        questions.add(new Question(44, "history","Saying the name of what dried fruit used to be used to encourage people to smile before a photo in the 1800s, before the phrase “cheese?”",new String[]{"figs","raisins","currants","prunes","mulberry","kumquats"},3));
        questions.add(new Question(45, "history","Divorced, beheaded, died, divorced, beheaded, survived – who was Henry VIII’s last wife? ",new String[]{"Catherine of Aragon","Anne Boleyn","Jane Seymour","Anne of Cleves","Catherine Parr","Catherine Howard"},4));
        questions.add(new Question(46, "history","When died the artist Pablo Picasso?",new String[]{"1413","1945","1673","1766","1873","1973"},5));
        questions.add(new Question(47, "history","Which year did the Vietnam War start?" ,new String[]{"1955","1973","1866","1933","1960","1913"},0));
	/*Question h8 = new Question(48, "history",,new String[]{,,,,,},);
	Question h9 = new Question(49, "history",,new String[]{,,,,,},);
	Question h10 = new Question(50, "history",,new String[]{,,,,,},);
	Question h11 = new Question(51, "history",,new String[]{,,,,,},);
	Question h12 = new Question(52, "history",,new String[]{,,,,,},);
	Question h13 = new Question(53, "history",,new String[]{,,,,,},);
	Question h14 = new Question(54, "history",,new String[]{,,,,,},);
	Question h15 = new Question(55, "history",,new String[]{,,,,,},);
	Question h16 = new Question(56, "history",,new String[]{,,,,,},);
	Question h17 = new Question(57, "history",,new String[]{,,,,,},);
	Question h18 = new Question(58, "history",,new String[]{,,,,,},);
	Question h19 = new Question(59, "history",,new String[]{,,,,,},);
	Question h20 = new Question(60, "history",,new String[]{,,,,,},);*/



        // politics
        questions.add(new Question(61, "politics","What is the middle name of Angela Merkel?",new String[]{"Dorothea","Henriette", "no middle name","Michaela","Bärbel","Viktoria"},0));
        questions.add(new Question(62, "politics","America's Republican Party is commonly referred to as the GOP - what does the GOP stand for?",new String[]{"Gap opening penalty","Grand Old Party","Gross Operating Profit","Group of Pictures","Great Opening Party","Georgspalast"},1));
        questions.add(new Question(63, "politics","Which country isn't one of the six founding members of the European Union?",new String[]{"West Germany","Belgium","Austria","Netherlands","France","Luxembourg"},2));
        questions.add(new Question(64, "politics","Which fashion designer reportedly had a relationship with Winston Churchill?",new String[]{"Betsey Johnson","Carolina Herrera","Diane Von Fürstenberg","Coco Chanel","Donatella Versace","Jeanne Lanvin"},3));
        //Question p5 = new Question(65, "politics",,new String[]{,,,,,},3);


        questions.add(new Question(66, "politics","In 1952 Albert Einstein was offered the Presidency of which country?",new String[]{"Germany","Austria","Switzerland","Afghanistan","Israel","Bolivia"},4));
        questions.add(new Question(67, "politics","In which state was former US President Barack Obama born?",new String[]{"Alabama","New York City","Colorado","Pennsylvania","Idaho","Hawaii"},5));
	/*Question p8 = new Question(68, "politics",,new String[]{,,,,,},0);
	Question p9 = new Question(69, "politics",,new String[]{,,,,,},1);
	Question p10 = new Question(70, "politics",,new String[]{,,,,,},2);
	Question p11 = new Question(71, "politics",,new String[]{,,,,,},3);
	Question p12 = new Question(72, "politics",,new String[]{,,,,,},4);
	Question p13 = new Question(73, "politics",,new String[]{,,,,,},5);
	Question p14 = new Question(74, "politics",,new String[]{,,,,,},0);
	Question p15 = new Question(75, "politics",,new String[]{,,,,,},1);
	Question p16 = new Question(76, "politics",,new String[]{,,,,,},2);
	Question p17 = new Question(77, "politics",,new String[]{,,,,,},3);
	Question p18 = new Question(78, "politics",,new String[]{,,,,,},4);
	Question p19 = new Question(79, "politics",,new String[]{,,,,,},5);
	Question p20 = new Question(70, "politics",,new String[]{,,,,,},2);*/



        // Foods and drinks
        questions.add(new Question(81, "foods and drinks","Which nuts are used in marzipan?",new String[]{"Almonds","Haselnuts","Walnuts","Chestnuts","Cashews","Pistachio"},0));
        questions.add( new Question(82, "foods and drinks","Which country is the origin of the cocktail Mojito?",new String[]{"Hawaii","Cuba","Costa Rica","Panama","Guatemala","Haiti"},1));
        questions.add(new Question(83, "foods and drinks","What is Japanese sake made from?",new String[]{"Potatoes","Soybeans","Rice","Corn","Sugar canes","Honey"},2));
        questions.add(new Question(84, "foods and drinks","What does IPA stand for?",new String[]{"Institute of Practitioners in Advertising","Identity, Policy and Audit","Isopropyl alcohol","Indian Pale Ale","Institute of Public Administration","Insolvency Practitioners Association"},3));
        questions.add( new Question(85, "foods and drinks","Which is the hottest chilli pepper in the wolrd?",new String[]{"7 Pot Douglah","Butch T","Naga Viper","Ghost Pepper","Carolina Reaper","Red Savina Habanero"},4));
        questions.add( new Question(86, "foods and drinks","According to McDonalds’ official website, how many calories does a regular Big Mac contain?",new String[]{"550","720","390","440","620","508"},5));
        questions.add(new Question(87, "foods and drinks","Gouda is a popular cheese originating from which country?",new String[]{"Netherlands","France","Italy","Germany","Belgium","Greece"},0));
        questions.add(new Question(88, "foods and drinks","Which southern Italian city is usually credited as the birthplace of the pizza?",new String[]{"Lecce","Naples","Bari","Amalfi","Palermo","Barletta"},1));
        questions.add( new Question(89, "foods and drinks","What number is a baker’s dozen?",new String[]{"24","3","13","15","12","9"},2));
        questions.add( new Question(90, "foods and drinks","Which day is observed as World Food Day?",new String[]{"3rd of January","12th May","27th July","16th of October","10th Dezember","20th of April"},3));
        questions.add( new Question(91, "foods and drinks","What is the type of Tomato fruit?",new String[]{"Herb","Vegetable","Shrubs","Flower","Berry","Fungi"},4));
	/*Question fd12 = new Question(92, "foods and drinks",,new String[]{,,,,,},5);
	Question fd13 = new Question(93, "foods and drinks",,new String[]{,,,,,},0);
	Question fd14 = new Question(94, "foods and drinks",,new String[]{,,,,,},1);
	Question fd15 = new Question(95, "foods and drinks",,new String[]{,,,,,},2);
	Question fd16 = new Question(96, "foods and drinks",,new String[]{,,,,,},3);
	Question fd17 = new Question(97, "foods and drinks",,new String[]{,,,,,},4);
	Question fd18 = new Question(98, "foods and drinks",,new String[]{,,,,,},5);
	Question fd19 = new Question(99, "foods and drinks",,new String[]{,,,,,},2);
	Question fd20 = new Question(100, "foods and drinks",,new String[]{,,,,,},3);*/



        // films
        questions.add(new Question(101, "films","Tom Cruise is an outspoken member of which religion?",new String[]{"Scientology","Christianity","Baptist Church","Presbyterian Church","Celebration Church","Evangelistic Church"},0));
        questions.add( new Question(102, "films","Which year was the original Toy Story film released in the US?",new String[]{"2000","1995","2002","1992","1999","1970"},1));
        questions.add( new Question(103, "films","The Social Network is a film about the founding of which major website?",new String[]{"Twitter","YouTube","Facebook","Instagram","Pinterest","Vimeo"},2));
        questions.add( new Question(104, "films","Which British actor plays Alfred Pennyworth in Joker (2019)?",new String[]{"Eric Wilton","William Austin","Ian Abercrombie","Douglas Hodge","Sean Pertwee","Michael Caine"},3));
        questions.add( new Question(105, "films","Which colour pill does Neo swallow in The Matrix?",new String[]{"blue","orange","green","black","red","yellow"},4));
        questions.add( new Question(106, "films","What type of car does Doc Brown use as a time machine in Back To The Future?",new String[]{"Jaguar","Camaro","Lotus Esprit","Cadillac","Chevrolet","DeLorean"},5));
        questions.add( new Question(107, "films","What is the best-selling novel of all time?",new String[]{"Don Quixote","The little prince","Harry Potter and the Philosophers Stone","the Hobbit","Alice's Adventures in Wonderland","The Da Vinci Code"},0));
        questions.add( new Question(108, "films","Who did Anne Hathaway play in Les Miserables?",new String[]{"Eponine","Fantine","Cosette","Madame Theanrdier","Azelma","Did not play any character"},1));
        questions.add( new Question(109, "films","Which former Doctor Who star played the role of a villain in Netflix Marvel series Jessica Jones?",new String[]{"Peter Capaldi","Matt Smith","David Tennant","Christopher Eccleston","Paul McGann","John Hunt"},2));
        questions.add( new Question(110, "films","Which Disney Princess called Gus and Jaq friends?",new String[]{"Mulan","Arielle","","Cinderella","Pocahontas","Merida"},3));
        questions.add( new Question(111, "films","Name the composer behind the soundtracks of The Lion King, Inception and Pirates of the Caribbean.",new String[]{"John Williams","James Horner","Michale Giacchino","Howard Shore","Hans Zimmer","Randy Edelman"},4));
        questions.add( new Question(112, "films","What was the first film to be released in the Marvel Cinematic Universe?",new String[]{"Doctor Strange","Black Panther","The incredible Hulk","Captain America: The first Avenger","The Avengers","Iron Man"},5));
        questions.add( new Question(113, "films","What causes John Wick to return to his old life of crime in John Wick?",new String[]{"His dog's murder","His wife's death","Boredom","A bad haircut","Bad weather","Nothing from these"},0));
        questions.add( new Question(114, "films","What type of creature does Luke Skywalker fight underneath Jabba the Hutt’s throne in Return of the Jedi?",new String[]{"Porg","A rancor","Anooba","Shaak","Nexus","Eopie"},1));
        questions.add( new Question(115, "films","In Star Trek: The Original Series, who was Captain in the pilot episode before Captain Kirk?",new String[]{"Captain John Archer","Captain Benjamin Sisko","Captain Pike","Captain Kathryn Janeway","Captain Jean-Luc Picard","Captain Luke"},2));
        questions.add( new Question(116, "films","In Monsters Inc. what is Sulley’s full name?",new String[]{"Sullivan James","Sulley The Monster","Sully is the full name","James P. Sullivan","Mike Sullivan","Peter J. Sullivan"},3));
        questions.add( new Question(117, "films","Who is Fluffy from Harry Potter?",new String[]{"Kelpie","Niffler","Phoenix","Unicorns","Three-headed dog","Griffin"},4));
        questions.add( new Question(118, "films","How many actors have played the role of James Bond?",new String[]{"5","13","15","7","11","9"},5));
        questions.add( new Question(119, "films","Which 2014 Seth Rogan film caused the North Korean government to threaten action against the United States?",new String[]{"The Interview","The 40-Year-Old Virgin","Pineapple Express","Neighbours","Sausage Party","The Watch"},0));
        questions.add( new Question(120, "films","Golden Raspberry Award for Worst Film 2019?",new String[]{"Holmes & Watson","Cats","Fifty Shades Darker","A Madea Family Funeral","The Haunting of Sharon Tate","The Fanatic"},1));



        // music
        questions.add(new Question(121, "music","Which singer has the most UK Number One singles ever?",new String[]{"Elvis Presley","Beatles","Cliff Richard","Madonna","Take That","Calvin Harris"},0));
        questions.add(new Question(122, "music","What was Britney Spears’ first single called?",new String[]{"Toxic","Baby One More Time","Ooops! I did it again","Criminal","Womanizer","Work Bitch"},1));
        questions.add(new Question(123, "music","Who is the only singer to have ever performed more than one James Bond theme song?",new String[]{"Paul McCartney & Wings","Sheena Easton","Shirley Bassey","Madonna","Billie Eilish","Tina Turner"},2));
        questions.add(new Question(124, "music","Who is the only musician ever to have been awarded the Nobel prize for literature?",new String[]{"Michale Jackson","Elvis Presley","Jimi Hendrix","Bob Dylan","David Bowie","Elton John"},3));
        questions.add(new Question(125, "music","Which Beatles song was banned from the BBC for its lyrics?",new String[]{"Hey Jude","Yellow Submarine","All you need is love","Paperback Writer","I am the Walrus","If I fell"},4));
        questions.add(new Question(126, "music","What is the real name of U2’s guitarist, known as The Edge?",new String[]{"Paul Hewson","Larry Mullen","Adam Clayton","Richard Evans","Jackie Hayden","David Evans"},5));
        questions.add(new Question(127, "music","What is David Bowie’s real name?",new String[]{"David Jones","David Miller","Johnson Bowie","Michael Bowie","David Hayden","John Oseary"},0));
        questions.add(new Question(128, "music","Which singer was known amongst other things as 'The King of Pop'?",new String[]{"Justin Biber","Michael Jackson","Bob Dylan","David Evans","David Bowie","Elvis Presley"},1));
        questions.add(new Question(129, "music","What is Cher's last name?",new String[]{"Smith","Williams","Sarkisian","Johnson","McAllister","Loveling"},2));
        questions.add( new Question(130, "music","American singer Stefani Joanne Angelina Germanotta is best known by which stagename?",new String[]{"Nicki Minaj","Katy Perry","Nina Dobrev","Lady Gaga","Alicia Keys","Audrey Hepburn"},3));
        questions.add( new Question(131, "music","In what year did The Beatles split up?",new String[]{"never","1973","1985","1966","1970","1979"},4));
        questions.add( new Question(132, "music","John Denver’s Take Me Home Country Roads is about which US state?",new String[]{"Alabama","Arkensas","Nevada","North Carolina","Oklahoma","West Virginia"},5));
        questions.add( new Question(133, "music","Gordon Sumner is the real name of what famous British musician?",new String[]{"Sting","Eurythmics","Wham!","Take That","Will Young","Olly Murs"},0));
        questions.add( new Question(134, "music","Eye of the Tiger is from the soundtrack of which 80s film?",new String[]{"Gremlins","Rocky III","Beverly Hills Cop","Raging Bull","Aliens","Labyrinth"},1));
        questions.add( new Question(135, "music","Which American singer-songwriter sang of falling into a Ring Of Fire?",new String[]{"Keith Whitley","Randy Travis","Johnny Cash","Ray Price","Charley PRide","Buck Owens"},2));
	/*Question m16 = new Question(136, "music",,new String[]{,,,,,},3);
	Question m17 = new Question(137, "music",,new String[]{,,,,,},4);
	Question m18 = new Question(138, "music",,new String[]{,,,,,},5);
	Question m19 = new Question(139, "music",,new String[]{,,,,,},4);
	Question m20 = new Question(140, "music",,new String[]{,,,,,},5);*/



        // animals and plants and humans -> nature
        questions.add(new Question(141, "nature","How many hearts does an octopus have?",new String[]{"3","1","2","8","6","5"},0));
        questions.add(new Question(142, "nature","What is the national animal of Scotland?",new String[]{"Dragon","Unicorn","Moster of Loch Ness" ,"Bear","Dog","Snake"},1));
        questions.add(new Question(143, "nature","How many permanent teeth does a dog have?",new String[]{"32","12","42","24","35","17"},2));
        questions.add(new Question(144, "nature","Night blindness is caused due to deficiency of Vitamin...?",new String[]{"B2","D","C","A","K","B12"},3));
        questions.add(new Question(145, "nature","What part of a plant conducts photosynthesis?",new String[]{"roots","stem","fruit","flowers","leaf","seeds"},4));
        questions.add(new Question(146, "nature","How many valves does the heart have?",new String[]{"1","2","3","5","6","4"},5));
        questions.add(new Question(147, "nature","What's the biggest animal in the world?",new String[]{"Blue Whale","Lion","Giraffe","Elephant","Alligator","Buffalo"},0));
        questions.add(new Question(148, "nature","What's a baby rabbit called?",new String[]{"kitten","kit","calf","pullet","cub","baby"},1));
        questions.add(new Question(149, "nature","What is the Ph value of human blood?",new String[]{"7.0","9.2","7.4","5.6","14.0","0.0"},2));
        questions.add( new Question(150, "nature","Number of bones in human body?",new String[]{"192","231","175","206","301","244"},3));
        questions.add( new Question(151, "nature","Entomology is the science that studies?",new String[]{"humans","lungs","fishes","oceans","insects","plants"},4));
        questions.add(new Question(152, "nature","Which process converts sugar to acids, alcohol or gases?",new String[]{"burning","deposition","Decarboxylation","depolymerization","lamination","fermentation"},5));
        questions.add(new Question(153, "nature","What's the strongest muscle in the human body based on it's weight?",new String[]{"The masseter - the main jaw muscle","the tongue","Gluteus Maximus","Soleus","Heart","External Muscles of the Eye"},0));
	/*Question n14 = new Question(154, "nature",,new String[]{,,,,,},1);
	Question n15 = new Question(155, "nature",,new String[]{,,,,,},2);
	Question n16 = new Question(156, "nature",,new String[]{,,,,,},3);
	Question n17 = new Question(157, "nature",,new String[]{,,,,,},4);
	Question n18 = new Question(158, "nature",,new String[]{,,,,,},5);
	Question n19 = new Question(159, "nature",,new String[]{,,,,,},4);
	Question n20 = new Question(160, "nature",,new String[]{,,,,,},5);*/



        // science
        questions.add(new Question(141, "science","How many elements are in the periodic table?",new String[]{"118","89","123","101","93","99"},0));
        questions.add( new Question(142, "science","How many kilometers in a mile?",new String[]{"1km","1.6km","1.4km","1.2km","1.8km","2km"},1));
        questions.add( new Question(143, "science","Light year is a unit of?",new String[]{"Light","time","Distance","intensity of light","sound","temperature"},2));
        questions.add( new Question(144, "science","What does GIF stand for?",new String[]{"German-Israeli Foundation for Scientific Research & Development","Kanton Gif-sur-Yvette","Generation IV International Forum","Graphics Interchange Format","Global Indian Foundation","Governance Interoperability Framework"},3));
        questions.add( new Question(145, "science","Light from the Sun reaches us in nearly?",new String[]{"few seconds","2 minutes","half an hour","1 hour","8 minutes","16 minutes"},4));
        questions.add( new Question(146, "science","In which year was Pulitzer Prize established?",new String[]{"1890","1999","2005","1945","1988","1917"},5));
        questions.add( new Question(147, "science","Which of the following is used in pencils?",new String[]{"graphite","silicone","charcoal","phosphorus","black dye","ink"},0));
        questions.add( new Question(148, "science","The average salinity of sea water is...?",new String[]{"1%","3.5%","5%","20%","70%","12%"},1));
        questions.add( new Question(149, "science","Potassium nitrate is used in...?",new String[]{"salt","glass","fertiliser","hairspray","medicine","paint"},2));
        questions.add( new Question(150, "science","When is the World Population Day observed?",new String[]{"1st of January","31st of December","1st of July","11th of July","25th of August","4th of April"},3));
	    /*Question sc11 = new Question(151, "science",,new String[]{,,,,,},4);
	    Question sc12 = new Question(152, "science",,new String[]{,,,,,},5);
	    Question sc13 = new Question(153, "science",,new String[]{,,,,,},0);
	    Question sc14 = new Question(154, "science",,new String[]{,,,,,},1);
	    Question sc15 = new Question(155, "science",,new String[]{,,,,,},2);
	    Question sc16 = new Question(156, "science",,new String[]{,,,,,},3);
	    Question sc17 = new Question(157, "science",,new String[]{,,,,,},4);
	    Question sc18 = new Question(158, "science",,new String[]{,,,,,},5);
	    Question sc19 = new Question(159, "science",,new String[]{,,,,,},2);
	    Question sc20 = new Question(160, "science",,new String[]{,,,,,},3);*/

        for (Question  question: questions) {
            Gate.createQuestionOnServer(question.getCategory(), question.getText(), question.getAnswers(), question.getCorrectAnswer());
        }
    }
}
