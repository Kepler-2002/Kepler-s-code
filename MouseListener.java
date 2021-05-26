package com.sf.minesweeper.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Map;

import javax.swing.JOptionPane;

import com.sf.minesweeper.Client;
import com.sf.minesweeper.bean.GlobalVariable;
import com.sf.minesweeper.bean.MineLabel;
import com.sf.minesweeper.bean.Request;
import com.sf.minesweeper.bean.Response;
import com.sf.minesweeper.dialog.Win;
import com.sf.minesweeper.frame.SartFrame;
import com.sf.minesweeper.tools.Tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;

/**
 * 监听鼠标点击事件，用来监听游戏中扫雷的动作
 */
public class MouseListener extends MouseAdapter {
	/**
	 * 标记雷区点击次数,第一次游戏开始
	 */
	private int mousePressedCount=0; // 鼠标点击的次数
	private int expendedCount; // 雷块展开的数量
	// MineField mineField;

	SartFrame sartframe;
	boolean isDouble;
	int temp = Tools.totalMine;// 临时雷数
	boolean isStart;// 游戏是否开始

	int score = 0;
	int errornum = 0;

	int flag = 0;

//	///////////////////////////////////
	int i = 1;
//	///////////////////////////////////
	
	int msg = 0;

	public MouseListener(SartFrame sartframe) {
		this.sartframe = sartframe;
	}

	// reset和read对应的构造方法
	// reset:1
	// read:2
	public MouseListener(SartFrame sartframe, int flag) {
		this.sartframe = sartframe;
		this.flag = flag;
	}

	Response response = new Response();
	
	public void mousePressed(MouseEvent arg0) {

		Request request = new Request();
		request.setCommand(Tools.login_in);
		request.setRequestType(3+"");
		request.setUserId(GlobalVariable.userId);
		response = (Response) Client.operate(request);
		
		if (response.getPlayType_in_Game().equals("NONE")) {
			// 申请使用系统
			request.setCommand(Tools.login_in);
			request.setRequestType(0+"");
			request.setUserId(GlobalVariable.userId);
			response = (Response) Client.operate(request);
		}
		
		if (response.getPlayType_in_Game().equals(GlobalVariable.userId)) {
			
			if (i == 1) {
				if (response.getReminder_flagNum()!=-1) {
					temp = response.getReminder_flagNum();
					System.out.println("来自服务器的temp:"+temp);
				}
			}
			
			System.out.println("第" + (i++) + "次点击");

			if (i > 5) {
				// 将控制权交给另一个用户
				request.setCommand(Tools.login_in);
				request.setRequestType(1+"");
				request.setUserId(GlobalVariable.userId);
				request.setScore(score);
				request.setErrornum(errornum);
				request.setReminder_flagNum(temp);
				response = (Response) Client.operate(request);
				
				System.out.println(response.getScore()+"11111111111111");
				System.out.println(response.getErrornum()+"222222222222");
				
				System.out.println("次数用光，无法继续操作");
				JOptionPane.showMessageDialog(null, "次数用光，无法继续操作");
//			sartframe.dispose();
				// 当前用户次数用完之后，不允许当前用户继续排雷
				// 接下来需要将当前游戏状态保存，以便于另一个用户进入游戏时读取游戏数据
				synchronized(MouseListener.class) {
//					System.out.println("开始删除文件");
//					FileUtil.del("D:\\document\\workspace\\MakeMoney_OtherWorkspace\\MineSweeper\\data\\round.txt");
//					System.out.println("开始创建文件");
//					FileUtil.touch("D:\\document\\workspace\\MakeMoney_OtherWorkspace\\MineSweeper\\data\\round.txt");
					System.out.println("开始存档");
					sartframe.save("../data/round.txt");
					System.out.println("存档完成");
					//接下来需要通知所有的用户界面更新游戏界面
					FileWriter writer = new FileWriter("../data/msg.txt");
					writer.write((msg++)+"");
//					sartframe.read("../data/round.txt");
				}

				return;
			}
			System.out.println("第" + (i - 1) + "次点击");

			// 获取到点击的对象
			MineLabel mineLabel = (MineLabel) arg0.getSource();
			int d = arg0.getModifiersEx(); // 返回此事件的修饰符掩码
			int d1 = arg0.getModifiers();
			// 获取当前点击的雷块和其行列索引
			// 行 列 均从0开始
			int rowIndex = mineLabel.getRowIndex();
			int colIndex = mineLabel.getColIndex();
			
			
			
			// 记录雷区点击次数
			mousePressedCount++;

			// 如果是第一次点击雷区，则开始布雷
			if (mousePressedCount == 1) {
				sartframe.getMineField().buildMine(rowIndex, colIndex, flag);
			}

			mineLabel = sartframe.getMineField().getMineLabel()[rowIndex][colIndex];
			System.out.println(mineLabel.getRowIndex());
			System.out.println(mineLabel.getColIndex());
			System.out.println(mineLabel.isMine());
			
			
			// 不是第一次点击，此时雷区已经生成完毕
			// 需要进一步确定鼠标的点击事件是 左键、右键、双键
			// .............................1双键按下.............................
			if (d == InputEvent.BUTTON1_DOWN_MASK + InputEvent.BUTTON3_DOWN_MASK) {
				// 1 周围图片切换
				// 遍历所有的雷块
				for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
					for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
						mineLabel = sartframe.getMineField().getMineLabel()[x][y];
						if (!mineLabel.isExpanded() && !mineLabel.isFlag())
							mineLabel.setIcon(Tools.mineCount[0]);
					}
				}
				// 2 表情变惊讶
				sartframe.getMineState().getNewGame().setIcon(Tools.iiface2);
				// 3 记录双键点击标志
				isDouble = true;

				// ..............................2右键按下
				// 且雷块未展开:进行排雷....................................
			} else if (d1 == InputEvent.BUTTON3_MASK && !mineLabel.isExpanded()) {
				// 右键点击次数
				int clickCount = mineLabel.getRightClickCount();
				clickCount++;
				// 第一次，雷块>>>旗子
				if (clickCount == 1) {
					mineLabel.setIcon(Tools.iiflag);
					mineLabel.setRightClickCount(clickCount);
					mineLabel.setFlag(true);

					// 移除监听
					sartframe.getMineField().getMineLabel()[rowIndex][colIndex]
							.removeMouseListener(sartframe.getMineField().getMouseListener());

					if (mineLabel.isMine()) {
						// 当前的剩余雷数
						temp--;
						System.out.println("temp="+temp);
						// 总分+1
						score++;
						System.out.println("是雷，标记正确");
						// 总分+1
						sartframe.getMineState().setTotalMineG(score);
						
						isMind();
						
					} else {
						errornum++;
						System.out.println("不是雷，标记错误");
						sartframe.getMineState().setErrorAction(errornum);
						// 然后将标记的地方的方块的图案展示出来
						mineLabel.setIcon(Tools.mineCount[mineLabel.getMineCount()]);
					}

				}

				// 第二次，旗子>>>问号
				if (clickCount == 2) {
					mineLabel.setIcon(Tools.iiask0);
					mineLabel.setRightClickCount(clickCount);
					mineLabel.setFlag(false);
					if (mineLabel.isMine()) {
						// 当前的剩余雷数
						temp++;
						// 总分-1
						score--;
						sartframe.getMineState().setTotalMineG(score);
					} else {
						// 恢复
						errornum--;
						sartframe.getMineState().setErrorAction(errornum);
					}

				}
				// 第三次，问号>>>雷块
				if (clickCount == 3) {
					mineLabel.setIcon(Tools.iiblank);
					mineLabel.setRightClickCount(0);
				}
				// **************************3左键按下************************************
				// 非雷区
			} else if (d1 == InputEvent.BUTTON1_MASK) {
				// 未展开且不是旗子(图片陷进去)
				if (!mineLabel.isExpanded() && !mineLabel.isFlag()) {
					// 问号
					if (mineLabel.getRightClickCount() == 2) {
						mineLabel.setIcon(Tools.iiask1);
						// 雷块
					} else {
						mineLabel.setIcon(Tools.mineCount[0]);
					}
					// 表情>>惊讶
					sartframe.getMineState().getNewGame().setIcon(Tools.iiface2);
				}

				if (mineLabel.isExpanded()) {
					sartframe.getMineState().getNewGame().setIcon(Tools.iiface0);
				}

			}
		}else {
			JOptionPane.showMessageDialog(null, "其他玩家现在正在游戏中，你无法操作");
		}

	}

	/**
	 * 鼠标释放
	 */
	public void mouseReleased(MouseEvent arg0) {

		MineLabel mineLabel = (MineLabel) arg0.getSource();

		// 获取点击的雷快的坐标
		int rowIndex = mineLabel.getRowIndex();
		int colIndex = mineLabel.getColIndex();

		int i = arg0.getModifiers();

		// ***************双键释放*********************************
		if (isDouble) {
			// 2 表情惊讶还原
			sartframe.getMineState().getNewGame().setIcon(Tools.iiface0);
			doubleReleased(rowIndex, colIndex, mineLabel.getMineCount());
			isDouble = false;
			isMind();

			// ***************左键释放********************************
		} else if (i == InputEvent.BUTTON1_MASK) {
			if (!mineLabel.isExpanded() && !mineLabel.isFlag()) {
				/**
				 * 鼠标第一次点击雷区时计时器开始计时
				 */

				if (!sartframe.isStart()) {
//					sartframe.getTimer().start();
					sartframe.setStart(true); // 游戏开始
				}
				// 踩到雷,当前分数-1
				if (mineLabel.isMine() && !mineLabel.isFlag()) {
					mineLabel.setIcon(Tools.iiblood);
//					openMine(rowIndex, colIndex);
					score--;
					sartframe.getMineState().setTotalMineG(score);
					// 然后将标记的地方的方块的图案展示出来
					//mineLabel.setIcon(Tools.mineCount[mineLabel.getMineCount()]);
					// 移除监听
					sartframe.getMineField().getMineLabel()[rowIndex][colIndex]
							.removeMouseListener(sartframe.getMineField().getMouseListener());

					sartframe.getMineState().getNewGame().setIcon(Tools.iiface3);

//					sartframe.getTimer().stop();
//					sartframe.setStart(false);

					// 没有踩到雷,展开安全雷区
				} else {
					score++;
					sartframe.getMineState().setTotalMineG(score);
					sartframe.getMineState().getNewGame().setIcon(Tools.iiface0);
					open(rowIndex, colIndex);
					isMind();
				}
			} else if (mineLabel.isExpanded()) {
				sartframe.getMineState().getNewGame().setIcon(Tools.iiface0);
			}
		}
	}

	public void doubleReleased(int rowIndex, int colIndex, int count) {
		/**
		 * flagBeside 周围8个位置的旗子数
		 * 
		 */
		int flagBeside = 0;
		for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
			for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
				if (sartframe.getMineField().getMineLabel()[x][y].isFlag()) {
					flagBeside++;
				}

			}
		}

		// 1 当前雷块未展开或者旗子数与数字不相等，图片还原
		if (!sartframe.getMineField().getMineLabel()[rowIndex][colIndex].isExpanded()
				|| sartframe.getMineField().getMineLabel()[rowIndex][colIndex].getMineCount() != flagBeside) {
			doublePressedBeside(rowIndex, colIndex, 2);
		}

		// 2 已经展开且周围雷数和旗子相等，判断周围8个方向是否有踩到雷(是雷，但没有旗子)
		if (sartframe.getMineField().getMineLabel()[rowIndex][colIndex].isExpanded()
				&& sartframe.getMineField().getMineLabel()[rowIndex][colIndex].getMineCount() == flagBeside) {
			boolean isBobm = false;
			for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
				for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
					if (sartframe.getMineField().getMineLabel()[x][y].isMine()
							&& !sartframe.getMineField().getMineLabel()[x][y].isFlag()) {
						isBobm = true;
						break;
					} else if (!sartframe.getMineField().getMineLabel()[x][y].isExpanded()) {
						open(x, y);
					}
				}
			}
			// 踩到雷
			if (isBobm) {
				openMine(rowIndex, colIndex);
			}

		}

	}

	public void doublePressedBeside(int rowIndex, int colIndex, int doubleType) {

		doublePressed(rowIndex, colIndex, doubleType);

		for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
			for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
				doublePressed(x, y, doubleType);
			}
		}

	}

	public void doublePressed(int i, int j, int doubleType) {
		MineLabel minelabel = sartframe.getMineField().getMineLabel()[i][j];

		if (!minelabel.isExpanded() && !minelabel.isFlag()) {

			if (doubleType == 1) {

				if (minelabel.getRightClickCount() == 2) {

					minelabel.setIcon(Tools.iiask1);

				} else {
					minelabel.setIcon(Tools.mineCount[0]);

				}
			} else {
				if (minelabel.getRightClickCount() == 2) {
					minelabel.setIcon(Tools.iiask0);
				} else {

					minelabel.setIcon(Tools.iiblank);

				}
			}
		}
	}

	/**
	 * 将所有的雷区展示出来

	 */
	public void openMine(int i, int j) {
		for (int m = 0; m < Tools.totalx; m++) {
			for (int n = 0; n < Tools.totaly; n++) {
				MineLabel mineLabel = sartframe.getMineField().getMineLabel()[m][n];
				// 是雷的情况 且不是旗子
				if (mineLabel.isMine() && !mineLabel.isFlag()) {
					// 踩雷的区域标红
					if (i == m && j == n) {
						mineLabel.setIcon(Tools.iiblood);
						// 其他区域正常展示
					} else {
						mineLabel.setIcon(Tools.iimine);
					}

					// 不是雷，是旗子
				} else if (!mineLabel.isMine() && mineLabel.isFlag()) {
					mineLabel.setIcon(Tools.iierror);
					sartframe.getMineState().getNewGame().setIcon(Tools.iiface3);
					sartframe.getTimer().stop();
					sartframe.setStart(false);

				}
				// 移除所有的鼠标事件
				mineLabel.removeMouseListener(sartframe.getMineField().getMouseListener());
			}
		}
	}

	/**
	 * 左键没有触雷，展开当前方块
	 */
	private void open(int rowIndex, int colIndex) {

		MineLabel mineLabel = sartframe.getMineField().getMineLabel()[rowIndex][colIndex];
		// 没有被展开的，且没有被标为红旗的方块
		if (!mineLabel.isExpanded() && !mineLabel.isFlag()) {
			// 获取雷区的所有雷块数量
			int count = mineLabel.getMineCount();

			mineLabel.setIcon(Tools.mineCount[count]);

			mineLabel.setExpanded(true);
			expendedCount++;

			// 如果当前方块周围雷区为0，则将周围方块全部展开
			if (count == 0) {
				for (int x = Math.max(rowIndex - 1, 0); x <= Math.min(rowIndex + 1, Tools.totalx - 1); x++) {
					for (int y = Math.max(colIndex - 1, 0); y <= Math.min(colIndex + 1, Tools.totaly - 1); y++) {
						open(x, y);
					}
				}
			}
		}

	}

	/**
	 * 判断所有的雷时候都排光了
	 */
	public void isMind() {
		
		System.out.println("剩余地雷数："+temp);
		
//		接下来开始按照游戏规则对玩家进行排名
		Request request = new Request();
		request.setCommand(Tools.login_in);
		//获取到游戏排名相关的信息
		request.setRequestType(4+"");
		request.setUserId(GlobalVariable.userId);
		response = (Response) Client.operate(request);
		
		Map<String, Integer> map = response.getScoreMap();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println("用户id:"+entry.getKey()+"\t用户总分:"+entry.getValue());
		}
		Map<String, Integer> map1 = response.getErrornumMap();
		for (Map.Entry<String, Integer> entry : map1.entrySet()) {
			System.out.println("用户id:"+entry.getKey()+"\t用户失误次数:"+entry.getValue());
		}
		
		//如果双方的分数差距大于游戏区中未揭晓的雷数，则直接判定优势方获胜。
		//获取当前用户的当前得分
		Integer localscole_temp = map.get(GlobalVariable.userId);
		//获取另一个用户的得分
		Integer otherscole_temp = null;
		//当前用户的失误数
		Integer localErrorNum_temp = map1.get(GlobalVariable.userId);
		//另一个用户的失误数
		Integer otherErrorNum_temp = null;
		//另一个玩家的id
		String otherId = null;
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (!entry.getKey().equals(GlobalVariable.userId)) {
				otherId = entry.getKey();
				otherscole_temp = entry.getValue();
			}
		}
		
		for (Map.Entry<String, Integer> entry : map1.entrySet()) {
			if (!entry.getKey().equals(GlobalVariable.userId)) {
				otherErrorNum_temp = entry.getValue();
			}
		}
		
		//获胜者的用户(默认为平局)
		String winnerId = "平局";
		
		//转换为int型
		int localscore = localscole_temp;
		int otherscore = otherscole_temp;
		int localErrorNum = localErrorNum_temp;
		int otherErrorNum = otherErrorNum_temp;
		//计算差值
		int d_value = Math.abs(localscore-otherscore);
		//当前差值大于剩余雷数，结束比赛
		if (d_value > temp) {
			// 遍历所有的方块
			for (int g = 0; g < Tools.totalx; g++)
				for (int h = 0; h < Tools.totaly; h++) {
					// 如果当前方块是雷块且没有被标注为红旗
					if (sartframe.getMineField().getMineLabel()[g][h].isMine()
							&& !sartframe.getMineField().getMineLabel()[g][h].isFlag()) {
						// 将当前方块标注为红旗
						sartframe.getMineField().getMineLabel()[g][h].setIcon(Tools.iiflag);
					}
					// 移除监听
					sartframe.getMineField().getMineLabel()[g][h]
							.removeMouseListener(sartframe.getMineField().getMouseListener());

				}
			sartframe.getMineState().getNewGame().setIcon(Tools.iiface4);

			sartframe.getMineState().setTotalMineG(0);

			sartframe.getTimer().stop();
			
			winnerId = localscore>otherscore ? GlobalVariable.userId:otherId;
			// 弹出获胜界面
			
			//游戏结束之前弹出结束原因
			JOptionPane.showMessageDialog(null, "游戏结束，因为分数相等，失误相等，平局");
			//"游戏结束，因为分数相等，失误少的一方获胜"
			//"游戏结束，因为当前两个在线的游戏玩家的比分差值大于剩余雷数"
			new Win(sartframe,winnerId);

			// 成功后弹出英雄记录版
			sartframe.setStart(false);
		}
		
		// TODO Auto-generated method stubt;
		//所有雷揭晓后
		if (temp == 0) {

			System.out.println("所有的雷都排光了！！！");
			
			//1.分数高者获胜
			if (localscore!=otherscore) {
				winnerId = localscore>otherscore ? GlobalVariable.userId:otherId;
				
				// 遍历所有的方块
				for (int g = 0; g < Tools.totalx; g++)
					for (int h = 0; h < Tools.totaly; h++) {
						// 如果当前方块是雷块且没有被标注为红旗
						if (sartframe.getMineField().getMineLabel()[g][h].isMine()
								&& !sartframe.getMineField().getMineLabel()[g][h].isFlag()) {
							// 将当前方块标注为红旗
							sartframe.getMineField().getMineLabel()[g][h].setIcon(Tools.iiflag);
						}
						// 移除监听
						sartframe.getMineField().getMineLabel()[g][h]
								.removeMouseListener(sartframe.getMineField().getMouseListener());

					}
				sartframe.getMineState().getNewGame().setIcon(Tools.iiface4);

				sartframe.getMineState().setTotalMineG(0);

				sartframe.getTimer().stop();
				
				// 弹出获胜界面
				JOptionPane.showMessageDialog(null, "游戏结束，因为分数高者获胜");
				new Win(sartframe,winnerId);
			}
			//2.分数相等，失误少的一方获胜。
			else if(localErrorNum != otherErrorNum){
				winnerId = localErrorNum>otherErrorNum ? GlobalVariable.userId:otherId;
				// 遍历所有的方块
				for (int g = 0; g < Tools.totalx; g++)
					for (int h = 0; h < Tools.totaly; h++) {
						// 如果当前方块是雷块且没有被标注为红旗
						if (sartframe.getMineField().getMineLabel()[g][h].isMine()
								&& !sartframe.getMineField().getMineLabel()[g][h].isFlag()) {
							// 将当前方块标注为红旗
							sartframe.getMineField().getMineLabel()[g][h].setIcon(Tools.iiflag);
						}
						// 移除监听
						sartframe.getMineField().getMineLabel()[g][h]
								.removeMouseListener(sartframe.getMineField().getMouseListener());

					}
				sartframe.getMineState().getNewGame().setIcon(Tools.iiface4);

				sartframe.getMineState().setTotalMineG(0);

				sartframe.getTimer().stop();
				
				// 弹出获胜界面
				JOptionPane.showMessageDialog(null, "游戏结束，因为分数相等，失误少的一方获胜");
				new Win(sartframe,winnerId);
			}
			
			// 成功后弹出英雄记录版
			sartframe.setStart(false);
		}

	}

}
