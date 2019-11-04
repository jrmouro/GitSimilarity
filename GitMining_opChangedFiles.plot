set title 'GitMining - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = cos((0.756538665435128 - (3.14159265359 - cos((cos(x) * x)))))
plot f(x) title 'cos((0.756538665435128 - (3.14159265359 - cos((cos(x) * x)))))', '/home/ronaldo/Documentos/GitSimilarity/GitMining_changedFilesData.txt' w p ls 1 title 'points'