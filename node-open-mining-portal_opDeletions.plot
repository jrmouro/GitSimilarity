set title 'node-open-mining-portal - Deletions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (sin(1.0) * log(cos((sin((-1.0 - x)) + (0.4558173349082013 / x)))))
plot f(x) title '(sin(1.0) * log(cos((sin((-1.0 - x)) + (0.4558173349082013 / x)))))', '/home/ronaldo/Documentos/GitSimilarity/node-open-mining-portal_deletionsData.txt' w p ls 1 title 'points'