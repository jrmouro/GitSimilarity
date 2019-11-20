set title 'GitMining - Deletions'
set xlabel 'tempo'
set ylabel 'volume'
set grid
set xrange [0.0:1.0]
set yrange [0.0:1.0]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (((x-cos(x)-0.0)-(cos(x)/(sin(-36.97582165964884)+cos(x)+0.0)/1.0)-0.0)*(log(x)*sin(x)*1.0)*1.0)
plot f(x) title '(((x-cos(x)-0.0)-(cos(x)/(sin(-36.97582165964884)+cos(x)+0.0)/1.0)-0.0)*(log(x)*sin(x)*1.0)*1.0)', '/home/ronaldo/Documentos/GitSimilarity/temp.txt' w p ls 1 title 'points'