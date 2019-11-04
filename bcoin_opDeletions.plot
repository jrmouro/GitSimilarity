set title 'bcoin - Deletions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (cos((0.7438268931754753 + cos((sin(sin((0.7438268931754753 / x))) * 1.0)))) / 0.7438268931754753)
plot f(x) title '(cos((0.7438268931754753 + cos((sin(sin((0.7438268931754753 / x))) * 1.0)))) / 0.7438268931754753)', '/home/ronaldo/Documentos/GitSimilarity/bcoin_deletionsData.txt' w p ls 1 title 'points'