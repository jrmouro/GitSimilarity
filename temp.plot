set title 'GitMining - Deletions'
set xlabel 'tempo'
set ylabel 'volume'
set grid
set xrange [0.0:1.0]
set yrange [0.0:1.0]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (log(x)*((x*(cos(1.0)-cos(x)-0.0)*1.0)-((sin(x)/sin(1.0)/1.0)*((1.0)-x-0.0)*1.0)-0.0)*1.0)
plot f(x) title '(log(x)*((x*(cos(1.0)-cos(x)-0.0)*1.0)-((sin(x)/sin(1.0)/1.0)*((1.0)-x-0.0)*1.0)-0.0)*1.0)', '/home/ronaldo/Documentos/GitSimilarity/temp.txt' w p ls 1 title 'points'