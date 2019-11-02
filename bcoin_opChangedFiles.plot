set title 'bcoin - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = ((x - 1.0) * (exp(-1.0) - ((0.0 * 0.0)**-1.0 + exp(0.0)**3.14159265359)))
plot f(x) title '((x - 1.0) * (exp(-1.0) - ((0.0 * 0.0)**-1.0 + exp(0.0)**3.14159265359)))', '/home/ronaldo/Documentos/GitSimilarity/bcoin_changedFilesData.txt' w p ls 1 title 'points'