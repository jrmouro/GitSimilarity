set title 'tpot - Insertions'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (sin(log(cos(0.2210477312491913))) * (log(x) - ((cos(sin(-1.0)) * 3.14159265359) / 3.14159265359)))
plot f(x) title '(sin(log(cos(0.2210477312491913))) * (log(x) - ((cos(sin(-1.0)) * 3.14159265359) / 3.14159265359)))', '/home/ronaldo/Documentos/GitSimilarity/tpot_insertionsData.txt' w p ls 1 title 'points'