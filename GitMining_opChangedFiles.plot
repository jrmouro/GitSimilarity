set title 'GitMining - ChangedFiles'
set xlabel 'time'
set ylabel 'volume'
set grid
set xrange [0:1]
set yrange [0:1]
set style line 1 lc rgb '#0060ad' pt 7 ps 0.5 lt 1 lw 2
f(x) = cos((3.14159265359 - (exp(cos(0.0)) - cos(x))))
plot f(x) title 'cos((3.14159265359 - (exp(cos(0.0)) - cos(x))))', '/home/ronaldo/NetBeansProjects/GitSimilarity/GitMining_changedFilesData.txt' w p ls 1 title 'points'