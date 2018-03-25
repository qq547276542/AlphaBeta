class AB {  
    static int round=1;  
    static int n=Jframe.n;  
    static int G[][]=new int[20][20];  
    static int INF=10000;  
    static void copy(int a[][],int b[][]){  
        for(int i=0;i<=n;i++){  
            for(int j=0;j<=n;j++){  
                b[i][j]=a[i][j];  
            }  
        }  
    }  
    static int checkVictory(int G[][]){   //黑方胜利返回1，白方胜利返回-1，还没分出胜负返回0  
        for(int i=0;i<=n;i++){  
            int sum=0,now=0;   //对于每行，从左向右扫  
            for(int j=0;j<=n;j++){     
                if(G[i][j]!=now){  
                    now=G[i][j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==5){  
                    return now;  
                }  
            }  
            sum=0;now=0;   //对于每列，从右向左扫  
            for(int j=0;j<=n;j++){  
                if(G[j][i]!=now){  
                    now=G[j][i];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==5){  
                    return now;  
                }  
            }  
            sum=0;now=0;  
            for(int j=0;i+j<=n;j++){  
                if(G[i+j][j]!=now){  
                    now=G[i+j][j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==5){  
                    return now;  
                }  
            }  
            sum=0;now=0;  
            for(int j=0;i+j<=n;j++){  
                if(G[j][i+j]!=now){  
                    now=G[j][i+j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==5){  
                    return now;  
                }  
            }  
            sum=0;now=0;  
            for(int j=0;i-j>=0;j++){  
                if(G[j][i-j]!=now){  
                    now=G[j][i-j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==5){  
                    return now;  
                }  
            }  
            sum=0;now=0;  
            for(int j=0;i+j<=n;j++){  
                if(G[i+j][n-j]!=now){  
                    now=G[i+j][n-j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==5){  
                    return now;  
                }  
            }  
        }  
        return 0;  
    }  
    static int max(int a,int b){  
        if(a>b)return a;  
        return b;  
    }  
    static int min(int a,int b){  
        if(a<b)return a;  
        return b;  
    }  
    static boolean hasround(int i,int j){ //如果周围没子，先不考虑
    	int fx[]={-2,-1,0,1,2};
    	int fy[]={-2,-1,0,1,2};
    	for(int a=0;a<5;a++){
    		for(int b=0;b<5;b++){
    			if(fx[a]==0&&fy[b]==0)
    				continue;
    			int newx=i+fx[a],newy=j+fy[b];
    			if(newx<0||newx>n||newy<0||newy>n)continue;
    			if(G[newx][newy]!=0)
    				return true;
    		}
    	}
    	return false;
    }
    static int alphabeta(int G[][],int player,int alpha,int beta,int depth){  
        if(depth==2) return getScore(G);  
        for(int i=0;i<=n;i++){  
            for(int j=0;j<=n;j++){  
                if(G[i][j]!=0)continue;  
                if(!hasround(i,j))continue;
                int curG[][]=new int[20][20];  
                copy(G,curG);  
                curG[i][j]=player;  
                int v=alphabeta(curG,player*-1,alpha,beta,depth+1);  
                if(player==-1)alpha=max(alpha,v); else beta=min(beta,v);  
                if(beta<=alpha) break;  
            }  
        }  
        if(player==-1) return alpha;  
        return beta;  
    }  
    /* 
     *  
     初步决定估值为双方活三数和活二数。 
           以黑白方不同而参数权重不同。 
           如果己方占据先机，则估值以自己活三活二数为重。 
           如果己方不利，则尽量消除对方活二数。 
           一条线连续两个子并且该线两子无对方子为活二。（不连续，两子之间有一空格也算活二） 
           活三则是在活二基础上下一个子。 
           死三是活三被对方防守一子。 
           活四……如果有活四，就已经胜利了。 
           有死四或者活三，就必须要防守了。 
           以这些参数乘以权值，作为估值函数。 
     */  
    static int getScore(int G[][]){  
        int score=0;  
        // [0]代表ai,[2]代表我方  
        int h2[]=new int[3],h3[]=new int[3],h4[]=new int [3];  
        //int h2=0,h3=0,h4=0,h5=0;  
        int s3[]=new int[3],s4[]=new int [3];  
        int hs5[]=new int [3];  
          
        for(int i=0;i<=n;i++){  
            int sum=0,now=0;   //对于每行，从左向右扫  
            for(int j=0;j<=n;j++){     
                if(G[i][j]!=now){  
                    now=G[i][j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==2){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[i][j-sum]==0&&G[i][j+1]==0)  
                            h2[now+1]++;  
                }else if(sum==3){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[i][j-sum]==0&&G[i][j+1]==0)  
                            h3[now+1]++;  
                    s3[now+1]++;  
                }else if(sum==4){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[i][j-sum]==0&&G[i][j+1]==0)  
                            h4[now+1]++;  
                    s4[now+1]++;  
                }else if(sum==5){  
                    hs5[now+1]++;  
                }  
            }  
            sum=0;now=0;   //对于每列，从右向左扫  
            for(int j=0;j<=n;j++){  
                if(G[j][i]!=now){  
                    now=G[j][i];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==2){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[j-sum][i]==0&&G[j+1][i]==0)  
                            h2[now+1]++;  
                }else if(sum==3){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[j-sum][i]==0&&G[j+1][i]==0)  
                            h3[now+1]++;  
                    s3[now+1]++;  
                }else if(sum==4){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[j-sum][i]==0&&G[j+1][i]==0)  
                            h4[now+1]++;  
                    s4[now+1]++;  
                }else if(sum==5){  
                    hs5[now+1]++;  
                }  
            }  
            sum=0;now=0;  
            for(int j=0;i+j<=n;j++){  
                if(G[i+j][j]!=now){  
                    now=G[i+j][j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==2){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[i+j-sum][j-sum]==0&&G[i+j+1][j+1]==0)  
                            h2[now+1]++;  
                }else if(sum==3){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[i+j-sum][j-sum]==0&&G[i+j+1][j+1]==0)  
                            h3[now+1]++;  
                    s3[now+1]++;  
                }else if(sum==4){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[i+j-sum][j-sum]==0&&G[i+j+1][j+1]==0)  
                            h4[now+1]++;  
                    s4[now+1]++;  
                }else if(sum==5){  
                    hs5[now+1]++;  
                }  
            }  
            sum=0;now=0;  
            for(int j=0;i+j<=n;j++){  
                if(G[j][i+j]!=now){  
                    now=G[j][i+j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==2){  
                    if(j-sum>=0&&i+j+1<=n)  
                        if(G[j-sum][i+j-sum]==0&&G[j+1][i+j+1]==0)  
                            h2[now+1]++;  
                }else if(sum==3){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[j-sum][i+j-sum]==0&&G[j+1][i+j+1]==0)  
                            h3[now+1]++;  
                    s3[now+1]++;  
                }else if(sum==4){  
                    if(j-sum>=0&&j+1<=n)  
                        if(G[j-sum][i+j-sum]==0&&G[j+1][i+j+1]==0)  
                            h4[now+1]++;  
                    s4[now+1]++;  
                }else if(sum==5){  
                    hs5[now+1]++;  
                }  
            }  
            sum=0;now=0;  
            for(int j=0;i-j>=0;j++){  
                if(G[j][i-j]!=now){  
                    now=G[j][i-j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==2){  
                    if(j-sum>=0&&i-j-1>=0)  
                        if(G[j-sum][i-j+sum]==0&&G[j+1][i-j-1]==0)  
                            h2[now+1]++;  
                }else if(sum==3){  
                    if(j-sum>=0&&i-j-1>=0)  
                        if(G[j-sum][i-j+sum]==0&&G[j+1][i-j-1]==0)  
                            h3[now+1]++;  
                    s3[now+1]++;  
                }else if(sum==4){  
                    if(j-sum>=0&&i-j-1>=0)  
                        if(G[j-sum][i-j+sum]==0&&G[j+1][i-j-1]==0)  
                            h4[now+1]++;  
                    s4[now+1]++;  
                }else if(sum==5){  
                    hs5[now+1]++;  
                }  
            }  
            sum=0;now=0;  
            for(int j=0;i+j<=n;j++){  
                if(G[i+j][n-j]!=now){  
                    now=G[i+j][n-j];  
                    sum=1;  
                }else{  
                    if(now!=0)  
                    sum++;  
                }  
                if(sum==2){  
                    if(i+j+1<=n&&n-j+sum<=n)  
                        if(G[i+j-sum][n-j+sum]==0&&G[i+j+1][n-j-1]==0)  
                            h2[now+1]++;  
                }else if(sum==3){  
                    if(i+j+1<=n&&n-j+sum<=n)  
                        if(G[i+j-sum][n-j+sum]==0&&G[i+j+1][n-j-1]==0)  
                            h3[now+1]++;  
                    s3[now+1]++;  
                }else if(sum==4){  
                    if(i+j+1<=n&&n-j+sum<=n)  
                        if(G[i+j-sum][n-j+sum]==0&&G[i+j+1][n-j-1]==0)  
                            h4[now+1]++;  
                    s4[now+1]++;  
                }else if(sum==5){  
                    hs5[now+1]++;  
                }  
            }  
        }  
        score=h2[0]*10-h2[2]*8+h3[0]*100-h3[2]*100+s3[0]*20-s3[2]*20;  
        score+=h4[0]*1000-h4[2]*1500;  
        score+=s4[0]*30-s4[2]*30;  
        score+=hs5[0]*10000-hs5[2]*10000;  
        return score;  
    }  
}  