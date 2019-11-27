set title 'GitMining - Deletions'
set xlabel 'tempo'
set ylabel 'volume'
set grid
set xrange [0.0:1.0]
set yrange [0.0:1.0]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (((((sin(((1.0269092519102825)*1.00004056977529))*1.0544580483567791)-1.0512013862411707)/(-1.0-0.9586332785523549))*1.072109214787481)*1.402495121096945)
plot f(x) title '(((((sin(((1.0269092519102825)*1.00004056977529))*1.0544580483567791)-1.0512013862411707)/(-1.0-0.9586332785523549))*1.072109214787481)*1.402495121096945)', '/home/ronaldo/Documentos/GitSimilarity/temp.txt' w p ls 1 title 'points'