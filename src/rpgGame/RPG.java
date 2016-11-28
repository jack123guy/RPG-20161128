package rpgGame;



import java.io.*;
import java.lang.*;
import java.util.*;
/*
****************************************************
				所有基礎設定
****************************************************
*/
class Skill{
	//To make Skill List
	 int number;
	 String name;
	 double value;
	
	void To_Enemy(double atk,chara2 x){
		
		System.out.printf("You use %s.%n", name);
		
		if(atk-x.def<=0){
			System.out.printf("You deal %d damage.%n", 1);
			x.hp = x.hp-(1);
		}
		else{
			System.out.printf("You deal %d damage.%n", atk-x.def);
			x.hp = x.hp-(atk-x.def);
		}
	}
	
	void To_Self(double heal,chara y){
		y.hp = y.hp + heal;
	}
	
	String getName(){
		return name;
	}
	
	int getNum(){
		return number;
	}
	
	Skill(int number,String name,double dam){
		this.number = number;
		this.name = name;
		this.value = dam;
	}
}


class item{
	public int number;
	public String name;
	public double def;
}


//角色設定
class chara{
	//素質設定
	public double hp,mp,atk,def,matk,mdef;
	public double str,dex,inte,luc;
	public ArrayList<Skill> ski = new ArrayList<Skill>();
	//等級
	int lv = 1,exp = 0;
	int[] exp_cap = new int[99];
	
	/*================================
				 升級系統
	==================================*/
	chara(){
		exp_cap[0] = 20;
		for(int i=1;i<99;i++){
			if(i<10){
				exp_cap[i] = exp_cap[i-1]+20;
			}else if(i>10&&i<30){
				exp_cap[i] = exp_cap[i-1] + 200;
			}else if(i>30&&i<50){
				exp_cap[i] = exp_cap[i-1] + 1000;
			}else if(i>50&&i<90){
				exp_cap[i] = exp_cap[i-1] + 5000;
			}else{
				exp_cap[i] = (int) (exp_cap[i-1]*1.2);
			}
		}
		System.out.println();
	}
	
	public void lv_up(int income){
		exp = exp + income;
		if(exp>=exp_cap[lv-1]){
			System.out.println("Lv up!!"+'\n'+"You are now lv " + lv);
			lv = lv+1;
			exp = (exp+income)-exp_cap[lv-1];
		}
	}

}
//怪物角色設定
class chara2{
	public double hp,mp,atk,def,matk,mdef;
	public double str,dex,inte,luc;
	public int lv;
	public int earned_exp;
	public int drop_item;
	public String name;
}


/*
****************************************************
					職業
****************************************************
*/
class Knight extends chara{


	Knight(){
		super.str = 12;super.dex= 8;super.inte=3;super.luc=5;
		super.hp = 200+super.lv*55;super.mp=20+super.lv*5;super.atk=12+str*0.15;super.def=33+str*0.3;
		//技能
		
		super.ski.add(new Skill(1,"Rage Hit",super.hp*0.15+super.str*0.3));
		super.ski.add(new Skill(2,"Heal",100+0.2*str));
		if(super.lv==2){
			super.ski.add(new Skill(3,"Skill_3",super.hp*0.15+super.str*0.3));
		}
		
		//KnightSkill.add(new skill());
		System.out.println("You are a Knight"+'\n');
	}
}


class Assassin extends chara{
	
	Assassin(){
		super.str = 5;super.dex= 15;super.inte=3;super.luc=8;
		super.hp = 170+super.lv*20;super.mp=25+super.lv*5;super.atk=22+str*0.15;super.def=10+str*0.3;
		System.out.println("You are an Assassin"+ '\n');
	}
}


/*
****************************************************
					怪物
****************************************************
*/
class Goblin extends chara2{
	
	Goblin(){
		super.str = 4;super.dex= 5;super.inte=3;super.luc=1;super.lv=2;
		super.hp = 5+super.lv*20;super.mp=20+super.lv*5;super.atk=5+str*0.15;super.def=5+str*0.3;
		super.earned_exp = 15;
		super.name = "Goblin";
		System.out.println("You encounter "+ super.name + '\n');
	}
}


/*
****************************************************
				  創角選單
****************************************************
*/
class makechara{
	public chara pro = new chara();
	makechara(int in){
		if(in==1){
			pro = new Knight();
			//cont = true;
		}else if(in==2){
			pro = new Assassin();
			//cont = true;
		}
		else{
			System.out.println("Not yet done");
		}
	}
}


/*
****************************************************
					戰鬥設定
****************************************************
*/
class Battle{
	boolean a_alive = true; boolean b_alive = true;
	double a_move,b_move;
	
	void battle_menu(int a_menu,chara a, chara2 b){
			switch(a_menu){
				case 4:
					a_alive = false;
					b_alive = false;
					break;
				case 3:
					//show item menu
					System.out.println("show item menu");
					break;
				case 2:
					//show skill menu
					System.out.println("show skill menu");
					Iterator it = a.ski.iterator();
					while(it.hasNext()){
						Skill localSki = (Skill)it.next();
						System.out.println(localSki.getNum() + ". " + localSki.getName());
					}
					Scanner sc = new Scanner(System.in);
					int move = sc.nextInt();
					a.ski.get(move).To_Enemy(a.ski.get(move).value, b);
					
					break;
				case 1:
					b.hp = b.hp - (a.atk - b.def);
					System.out.println("\n\n"+"your hp = "+ Math.round(a.hp));
					System.out.println("Goblin's hp = "+Math.round(b.hp)+'\n');
					break;
				default:
				System.out.println("Error at a_menu");
				break;
			}
		}
		
	Battle(chara a, chara2 b){
		a_move = 0;b_move= 0;
		//chara aa = a;chara2 bb = b;
		
		System.out.println("Fight Start"+'\n');
		while(a_alive==b_alive){
			a_move=a.dex+a_move;
			b_move=b.dex+b_move;
			//a or b 行動
			if(a_move>=100 || b_move>=100){
				System.out.println('\n'+"your hp = "+Math.round(a.hp));
				System.out.println("Goblin's hp = "+Math.round(b.hp)+'\n');
			}
			
			if(a_move>=100){
				System.out.println("Your turn");
				System.out.println("1.Attack    2.Skill    3.Item    4.Escape");
				Scanner menu_scan = new Scanner(System.in);
				int a_menu = menu_scan.nextInt();
				battle_menu(a_menu,a,b);
				a_move = 0;
				menu_scan.close();
				
			}
			if(b_move>=100){
				double damage;
				if(b.atk-a.def<=0) damage=1;
				else damage = b.atk-a.def;
				System.out.println("Monster attack"+'\n');
				a.hp = a.hp-(damage);
				b_move=0;
			}
			
			
			//戰鬥結束判定
			if(a.hp<=0){
				System.out.println("You are Dead");
				a.hp=0;
				a_alive = false;
			}
			else if(b.hp<=0){
				System.out.println("You Win");
				a.lv_up(b.earned_exp);
				b.hp=0;
				b_alive=false;
			}
			
			
			//結束方法判定
			if(a_alive == false && b_alive ==false){
				System.out.println("You Escape");
				break;
				//go to GAME OVER
			}
			else if(a_alive == false){
				System.out.println("Game Over");
				break;
			}
		}
	}
}


//MAIN
public class RPG{
	//public static boolean cont = false;
	public static void main(String args[]){
		
		System.out.println("Welcome to the RPG GAME !");
		System.out.println("Choose your character :");
		System.out.println("1.Knight 2.Assassin 3.Priest 4.Mage");
		
		//Test Create Character
		Scanner in = new Scanner(System.in);
		int choose = in.nextInt();
		makechara you = new makechara(choose);
		in.close();

		//Test Battle
		System.out.println("Game start");
		System.out.println("First Encounter"+'\n');
		
		for(int battle_count=0;battle_count<5;battle_count++){
			Goblin mon = new Goblin();
			Battle testbattle = new Battle(you.pro,mon);
		}
		System.out.println("\n\n TEST OVER");
	}
}



