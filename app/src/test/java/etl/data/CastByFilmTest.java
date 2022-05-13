package etl.data;

public class CastByFilmTest {

/*
Films
8021e3b6,Knight and Day,2010
32adaa60,War of the Worlds,2005
72c0b6e5,Man on Fire,2004
7e510c52,i am sam,2001
2a7f9b66,The Bone Collector,1999
fd85472f,The Deer Hunter,1978
3309852a,The Bridges of Madison County,1995
f06f0118,The Intern,2015
75ea5b7b,The Pelican Brief,1992
712218b4,Outbreak,1995
58855c29,Rain Man,1988
59533bd6,Good Will Hunting,1997
65d03714,"Good Morning, Vietnam",1987
33acbd5a,The Birdcage,1996
8986b96b,Crimson Tide,1995
782bf84f,Enemy of the State,1998
0a2cc64e,Awakenings,1990

Actors
7c0f5849,Tom Cruise,1962,07,03,
4a7a4a9e,Sandra Bullock,1964,07,26,
cbe469fe,Tom Hanks,1956,07,09,
606db772,Cameron Diaz,1972,08,30,
b57d2fd4,Denzel Washington,1954,12,28,
fbcded80,Dakota Fanning,1994,02,23,
3f46cda2,Sean Penn,1960,08,17,
e96af57c,Angelina Jolie,1975,06,04,
5332551c,Meryl Streep,1949,06,22,
70c2d18c,Anne Hathaway,1982,11,12,
a8474cc0,Robert De Niro,1943,08,17,
62a1f007,Clint Eastwood,1930,05,31,
86d5dd58,Julia Roberts,1967,10,28,
c24662e5,Rene Russo,1954,02,17,
63e5497c,Dustin Hoffman,1937,08,08,
e82f1fa0,Robin Williams,1951,07,21,2014
d2ce124e,Matt Damon,1970,10,08,
3e453735,Ben Affleck,1972,08,15,
2e1be4a0,Gene Hackman,1930,01,30,
7dbf6b19,Will Smith,1968,09,25,

Casts
8021e3b6,7c0f5849,Roy Miller
8021e3b6,606db772,June Havens
32adaa60,7c0f5849,Ray Ferrier
32adaa60,fbcded80,Rachel Ferrier
72c0b6e5,b57d2fd4,John Creasy
72c0b6e5,fbcded80,Pita Ramos
7e510c52,3f46cda2,Samuel Dawson
7e510c52,fbcded80,Lucy Diamond Dawson
2a7f9b66,b57d2fd4,Lincoln Rhyme
2a7f9b66,e96af57c,Amelia Donaghy
fd85472f,a8474cc0,Michael Vronsky
fd85472f,5332551c,Linda
3309852a,62a1f007,Robert Kincaid
3309852a,5332551c,Francesca Johnson
f06f0118,a8474cc0,Ben Whittaker
f06f0118,70c2d18c,Jules Ostin
f06f0118,c24662e5,Fiona
75ea5b7b,86d5dd58,Darby Shaw
75ea5b7b,b57d2fd4,Gray Grantham
712218b4,63e5497c,Sam Daniels
712218b4,c24662e5,Robby Keough
58855c29,63e5497c,Ray Babbitt
58855c29,7c0f5849,Charlie Babbitt
59533bd6,e82f1fa0,Dr. Sean Maguire
59533bd6,d2ce124e,Will Hunting
59533bd6,3e453735,Chuckie Sullivan
65d03714,e82f1fa0,Adrian Cronauer
33acbd5a,e82f1fa0,Armand Goldman
33acbd5a,2e1be4a0,Kevin Keeley
8986b96b,b57d2fd4,Ron Hunter
8986b96b,2e1be4a0,Frank Ramsey
782bf84f,7dbf6b19,Bobby Dean
782bf84f,2e1be4a0,Brill Lyle
0a2cc64e,a8474cc0,Leonard Lowe
0a2cc64e,e82f1fa0,Malcolm Sayer

Cast by Film

fd85472f,The Deer Hunter,1978
fd85472f,a8474cc0,Michael Vronsky
         a8474cc0,Robert De Niro,1943,08,17,
fd85472f,5332551c,Linda
         5332551c,Meryl Streep,1949,06,22,

"1,01,The Deer Hunter,1978\n"
"2,01,1,Michael Vronsky,Robert De Niro,35\n"
"2.01,2,Linda,Meryl Streep,29\n"

65d03714,"Good Morning, Vietnam",1987
65d03714,e82f1fa0,Adrian Cronauer
         e82f1fa0,Robin Williams,1951,07,21,2014

"1,02,\"Good Morning, Vietnam\",1987\n"
"2,02,1,Adrian Cronauer,Robin Williams,36\n"

58855c29,Rain Man,1988
58855c29,63e5497c,Ray Babbitt
         63e5497c,Dustin Hoffman,1937,08,08,
58855c29,7c0f5849,Charlie Babbitt
         7c0f5849,Tom Cruise,1962,07,03,

"1,03,Rain Man,1988\n"
"2,03,1,Ray Babbitt,Dustin Hoffman,51\n"
"2,03,2,Charlie Babbitt,Tom Cruise,26\n"

0a2cc64e,Awakenings,1990
0a2cc64e,a8474cc0,Leonard Lowe
         a8474cc0,Robert De Niro,1943,08,17,
0a2cc64e,e82f1fa0,Malcolm Sayer
         e82f1fa0,Robin Williams,1951,07,21,2014

"1,04,Awakenings,1990\n"
"2,04,1,Leonard Lowe,Robert De Niro,47\n"
"2,04,2,Malcolm Sayer,Robin Williams,39\n"

75ea5b7b,The Pelican Brief,1992
75ea5b7b,86d5dd58,Darby Shaw
         86d5dd58,Julia Roberts,1967,10,28,
75ea5b7b,b57d2fd4,Gray Grantham
         b57d2fd4,Denzel Washington,1954,12,28,

"1,05,The Pelican Brief,1992\n"
"2,05,1,Darby Shaw,Julia Roberts,25\n"
"2,05,2,Gray Grantham,Denzel Washington,38\n"

8986b96b,Crimson Tide,1995
8986b96b,b57d2fd4,Ron Hunter
         b57d2fd4,Denzel Washington,1954,12,28,
8986b96b,2e1be4a0,Frank Ramsey
         2e1be4a0,Gene Hackman,1930,01,30,

"1,06,Crimson Tide,1995\n"
"2,06,1,Ron Hunter,Denzel Washington,41\n"
"2,06,2,Frank Ramsey,Gene Hackman,65\n"

712218b4,Outbreak,1995
712218b4,63e5497c,Sam Daniels
         63e5497c,Dustin Hoffman,1937,08,08,
712218b4,c24662e5,Robby Keough
         c24662e5,Rene Russo,1954,02,17,

"1,07,Outbreak,1995\n"
"2,07,1,Sam Daniels,Dustin Hoffman,58\n"
"2,07,2,Robby Keough,Rene Russo,41\n"

3309852a,The Bridges of Madison County,1995
3309852a,62a1f007,Robert Kincaid
         62a1f007,Clint Eastwood,1930,05,31,
3309852a,5332551c,Francesca Johnson
         5332551c,Meryl Streep,1949,06,22,

"1,08,The Bridges of Madison County,1995\n"
"2,08,1,Robert Kincaid,Clint Eastwood,65\n"
"2,08,2,Francesca Johnson,Meryl Streep,46\n"

33acbd5a,The Birdcage,1996
33acbd5a,e82f1fa0,Armand Goldman
         e82f1fa0,Robin Williams,1951,07,21,2014
33acbd5a,2e1be4a0,Kevin Keeley
         2e1be4a0,Gene Hackman,1930,01,30,

"1,09,The Birdcage,1996\n"
"2,09,1,Armand Goldman,Robin Williams,45\n"
"2,09,2,Kevin Keeley,Gene Hackman,66\n"

59533bd6,Good Will Hunting,1997
59533bd6,e82f1fa0,Dr. Sean Maguire
         e82f1fa0,Robin Williams,1951,07,21,2014
59533bd6,d2ce124e,Will Hunting
         d2ce124e,Matt Damon,1970,10,08,
59533bd6,3e453735,Chuckie Sullivan
         3e453735,Ben Affleck,1972,08,15,

"1,10,Good Will Hunting,1997\n"
"2,10,1,Dr. Sean Maguire,Robin Williams,46\n"
"2,10,2,Will Hunting,Matt Damon,27\n"
"2,10,3,Chuckie Sullivan,Ben Affleck,25\n"

782bf84f,Enemy of the State,1998
782bf84f,7dbf6b19,Bobby Dean
         7dbf6b19,Will Smith,1968,09,25,
782bf84f,2e1be4a0,Brill Lyle
         2e1be4a0,Gene Hackman,1930,01,30,

"1,11,Enemy of the State,1998\n"
"2,11,1,Bobby Dean,Will Smith,30\n"
"2,11,2,Brill Lyle,Gene Hackman,68\n"

2a7f9b66,The Bone Collector,1999
2a7f9b66,b57d2fd4,Lincoln Rhyme
         b57d2fd4,Denzel Washington,1954,12,28,
2a7f9b66,e96af57c,Amelia Donaghy
         e96af57c,Angelina Jolie,1975,06,04,

"1,12,The Bone Collector,1999\n"
"2,12,1,Lincoln Rhyme,Denzel Washington,45\n"
"2,12,2,Amelia Donaghy,Angelina Jolie,24\n"

7e510c52,i am sam,2001
7e510c52,3f46cda2,Samuel Dawson
         3f46cda2,Sean Penn,1960,08,17,
7e510c52,fbcded80,Lucy Diamond Dawson
         fbcded80,Dakota Fanning,1994,02,23,

"1,13,i am sam,2001\n"
"2,13,1,Samuel Dawson,Sean Penn,41\n"
"2,13,2,Lucy Diamond Dawson,Dakota Fanning,7\n"

72c0b6e5,Man on Fire,2004
72c0b6e5,b57d2fd4,John Creasy
         b57d2fd4,Denzel Washington,1954,12,28,
72c0b6e5,fbcded80,Pita Ramos
         fbcded80,Dakota Fanning,1994,02,23,

"1,14,Man on Fire,2004\n"
"2,14,1,John Creasy,Denzel Washington,50\n"
"2,14,2,Pita Ramos,Dakota Fanning,10\n"

32adaa60,War of the Worlds,2005
32adaa60,7c0f5849,Ray Ferrier
         7c0f5849,Tom Cruise,1962,07,03,
32adaa60,fbcded80,Rachel Ferrier
         fbcded80,Dakota Fanning,1994,02,23,

"1,15,War of the Worlds,2005\n"
"2,15,1,Ray Ferrier,Tom Cruise,43\n"
"2,15,2,Rachel Ferrier,Dakota Fanning,11\n"

8021e3b6,Knight and Day,2010
8021e3b6,7c0f5849,Roy Miller
         7c0f5849,Tom Cruise,1962,07,03,
8021e3b6,606db772,June Havens
         606db772,Cameron Diaz,1972,08,30,

"1,16,Knight and Day,2010\n"
"2,16,1,Roy Miller,Tom Cruise,48\n"
"2,16,2,June Havens,Cameron Diaz,38\n"

f06f0118,The Intern,2015
f06f0118,a8474cc0,Ben Whittaker
         a8474cc0,Robert De Niro,1943,08,17,
f06f0118,70c2d18c,Jules Ostin
         70c2d18c,Anne Hathaway,1982,11,12,
f06f0118,c24662e5,Fiona
         c24662e5,Rene Russo,1954,02,17,

"1,17,The Intern,2015\n"
"2,17,1,Ben Whittaker,Robert De Niro,72\n"
"2,17,2,Jules Ostin,Anne Hathaway,33\n"
"2,17,3,Fiona,Rene Russo,61"
*/

    public static String data =
    "1,01,The Deer Hunter,1978\n"
    + "2,01,1,Linda,Meryl Streep,29\n"
    + "2,01,2,Michael Vronsky,Robert De Niro,35\n"
    + "1,02,\"Good Morning, Vietnam\",1987\n"
    + "2,02,1,Adrian Cronauer,Robin Williams,36\n"
    + "1,03,Rain Man,1988\n"
    + "2,03,1,Charlie Babbitt,Tom Cruise,26\n"
    + "2,03,2,Ray Babbitt,Dustin Hoffman,51\n"
    + "1,04,Awakenings,1990\n"
    + "2,04,1,Malcolm Sayer,Robin Williams,39\n"
    + "2,04,2,Leonard Lowe,Robert De Niro,47\n"
    + "1,05,The Pelican Brief,1992\n"
    + "2,05,1,Darby Shaw,Julia Roberts,25\n"
    + "2,05,2,Gray Grantham,Denzel Washington,38\n"
    + "1,06,Crimson Tide,1995\n"
    + "2,06,1,Ron Hunter,Denzel Washington,41\n"
    + "2,06,2,Frank Ramsey,Gene Hackman,65\n"
    + "1,07,Outbreak,1995\n"
    + "2,07,1,Robby Keough,Rene Russo,41\n"
    + "2,07,2,Sam Daniels,Dustin Hoffman,58\n"
    + "1,08,The Bridges of Madison County,1995\n"
    + "2,08,1,Francesca Johnson,Meryl Streep,46\n"
    + "2,08,2,Robert Kincaid,Clint Eastwood,65\n"
    + "1,09,The Birdcage,1996\n"
    + "2,09,1,Armand Goldman,Robin Williams,45\n"
    + "2,09,2,Kevin Keeley,Gene Hackman,66\n"
    + "1,10,Good Will Hunting,1997\n"
    + "2,10,1,Chuckie Sullivan,Ben Affleck,25\n"
    + "2,10,2,Will Hunting,Matt Damon,27\n"
    + "2,10,3,Dr. Sean Maguire,Robin Williams,46\n"
    + "1,11,Enemy of the State,1998\n"
    + "2,11,1,Bobby Dean,Will Smith,30\n"
    + "2,11,2,Brill Lyle,Gene Hackman,68\n"
    + "1,12,The Bone Collector,1999\n"
    + "2,12,1,Amelia Donaghy,Angelina Jolie,24\n"
    + "2,12,2,Lincoln Rhyme,Denzel Washington,45\n"
    + "1,13,i am sam,2001\n"
    + "2,13,1,Lucy Diamond Dawson,Dakota Fanning,7\n"
    + "2,13,2,Samuel Dawson,Sean Penn,41\n"
    + "1,14,Man on Fire,2004\n"
    + "2,14,1,Pita Ramos,Dakota Fanning,10\n"
    + "2,14,2,John Creasy,Denzel Washington,50\n"
    + "1,15,War of the Worlds,2005\n"
    + "2,15,1,Rachel Ferrier,Dakota Fanning,11\n"
    + "2,15,2,Ray Ferrier,Tom Cruise,43\n"
    + "1,16,Knight and Day,2010\n"
    + "2,16,1,June Havens,Cameron Diaz,38\n"
    + "2,16,2,Roy Miller,Tom Cruise,48\n"
    + "1,17,The Intern,2015\n"
    + "2,17,1,Jules Ostin,Anne Hathaway,33\n"
    + "2,17,2,Fiona,Rene Russo,61\n"
    + "2,17,3,Ben Whittaker,Robert De Niro,72"
    ;
}
