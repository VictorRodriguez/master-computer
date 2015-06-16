#include <stdio.h>
#include <time.h>
#include <sys/time.h>
#define MAX 100000

static struct timeval tm1;

static inline void start()
{
    gettimeofday(&tm1, NULL);
}

static inline void stop()
{
    struct timeval tm2;
    gettimeofday(&tm2, NULL);

    unsigned long long t = 1000 * (tm2.tv_sec - tm1.tv_sec) + (tm2.tv_usec - tm1.tv_usec) / 1000;
    printf("%llu ms\n", t);

}

int main() {

    start();

    int r[MAX + 1];
    int i, k;
    int b, d;
    int c = 0;

    for (i = 0; i < MAX; i++) {
        r[i] = 2000;
    }

    for (k = MAX; k > 0; k -= 14) {
        d = 0;

        i = k;
        for (;;) {
            d += r[i] * 10000;
            b = 2 * i - 1;

            r[i] = d % b;
            d /= b;
            i--;
            if (i == 0) break;
            d *= i;
        }
        // printf("%.4d", c + d / 10000);
        c = d % 10000;
    }

    //printf("\n");
    stop();
    return 0;
}
