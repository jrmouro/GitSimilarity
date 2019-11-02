set title 'GitMining - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = (-1.0 / ((log(x) - 1.0) - exp((0.0 - 0.0))))
plot f(x) title '(-1.0 / ((log(x) - 1.0) - exp((0.0 - 0.0))))', '/home/ronaldo/Documentos/GitSimilarity/GitMining_changedFilesData.txt' w p ls 1 title 'points'